package com.espressif.iot.esptouch.task;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.espressif.iot.esptouch.EsptouchResult1;
import com.espressif.iot.esptouch.IEsptouchListener1;
import com.espressif.iot.esptouch.IEsptouchResult1;
import com.espressif.iot.esptouch.IEsptouchTask1;
import com.espressif.iot.esptouch.protocol.EsptouchGenerator1;
import com.espressif.iot.esptouch.protocol.TouchData;
import com.espressif.iot.esptouch.udp.UDPSocketClient1;
import com.espressif.iot.esptouch.udp.UDPSocketServer1;
import com.espressif.iot.esptouch.util.ByteUtil1;
import com.espressif.iot.esptouch.util.EspAES;
import com.espressif.iot.esptouch.util.EspNetUtil1;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class __EsptouchTask1 implements __IEsptouchTask1 {
    /**
     * one indivisible data contain 3 9bits info
     */
    private static final int ONE_DATA_LEN = 3;

    private static final String TAG = "__EsptouchTask1";

    private final UDPSocketClient1 mSocketClient;
    private final UDPSocketServer1 mSocketServer;
    private final byte[] mApSsid;
    private final byte[] mApPassword;
    private final byte[] mApBssid;
    private final boolean mIsSsidHidden;
    private final Context mContext;
    private volatile List<IEsptouchResult1> mEsptouchResultList;
    private volatile boolean mIsSuc = false;
    private volatile boolean mIsInterrupt = false;
    private volatile boolean mIsExecuted = false;
    private AtomicBoolean mIsCancelled;
    private IEsptouchTaskParameter1 mParameter;
    private volatile Map<String, Integer> mBssidTaskSucCountMap;
    private IEsptouchListener1 mEsptouchListener;
    private Thread mTask;

    public __EsptouchTask1(Context context, TouchData apSsid, TouchData apBssid, TouchData apPassword, EspAES espAES,
                           IEsptouchTaskParameter1 parameter, boolean isSsidHidden) {
        Log.i(TAG, "Welcome Esptouch " + IEsptouchTask1.ESPTOUCH_VERSION);
        mContext = context;
        if (espAES == null) {
            mApSsid = apSsid.getData();
            mApPassword = apPassword.getData();
        } else {
            mApSsid = espAES.encrypt(apSsid.getData());
            mApPassword = espAES.encrypt(apPassword.getData());
        }
        mApBssid = apBssid.getData();
        mIsCancelled = new AtomicBoolean(false);
        mSocketClient = new UDPSocketClient1();
        mParameter = parameter;
        mSocketServer = new UDPSocketServer1(mParameter.getPortListening(),
                mParameter.getWaitUdpTotalMillisecond(), context);
        mIsSsidHidden = isSsidHidden;
        mEsptouchResultList = new ArrayList<>();
        mBssidTaskSucCountMap = new HashMap<>();
    }

    private void __putEsptouchResult(boolean isSuc, String bssid, InetAddress inetAddress) {
        synchronized (mEsptouchResultList) {
            // check whether the result receive enough UDP response
            boolean isTaskSucCountEnough = false;
            Integer count = mBssidTaskSucCountMap.get(bssid);
            if (count == null) {
                count = 0;
            }
            ++count;
            if (__IEsptouchTask1.DEBUG) {
                Log.d(TAG, "__putEsptouchResult(): count = " + count);
            }
            mBssidTaskSucCountMap.put(bssid, count);
            isTaskSucCountEnough = count >= mParameter
                    .getThresholdSucBroadcastCount();
            if (!isTaskSucCountEnough) {
                if (__IEsptouchTask1.DEBUG) {
                    Log.d(TAG, "__putEsptouchResult(): count = " + count
                            + ", isn't enough");
                }
                return;
            }
            // check whether the result is in the mEsptouchResultList already
            boolean isExist = false;
            for (IEsptouchResult1 esptouchResultInList : mEsptouchResultList) {
                if (esptouchResultInList.getBssid().equals(bssid)) {
                    isExist = true;
                    break;
                }
            }
            // only add the result who isn't in the mEsptouchResultList
            if (!isExist) {
                if (__IEsptouchTask1.DEBUG) {
                    Log.d(TAG, "__putEsptouchResult(): put one more result");
                }
                final IEsptouchResult1 esptouchResult = new EsptouchResult1(isSuc,
                        bssid, inetAddress);
                mEsptouchResultList.add(esptouchResult);
                if (mEsptouchListener != null) {
                    mEsptouchListener.onEsptouchResultAdded(esptouchResult);
                }
            }
        }
    }

    private List<IEsptouchResult1> __getEsptouchResultList() {
        synchronized (mEsptouchResultList) {
            if (mEsptouchResultList.isEmpty()) {
                EsptouchResult1 esptouchResultFail = new EsptouchResult1(false,
                        null, null);
                esptouchResultFail.setIsCancelled(mIsCancelled.get());
                mEsptouchResultList.add(esptouchResultFail);
            }

            return mEsptouchResultList;
        }
    }

    private synchronized void __interrupt() {
        if (!mIsInterrupt) {
            mIsInterrupt = true;
            mSocketClient.interrupt();
            mSocketServer.interrupt();
            // interrupt the current Thread which is used to wait for udp response
            if (mTask != null) {
                mTask.interrupt();
                mTask = null;
            }
        }
    }

    @Override
    public void interrupt() {
        if (__IEsptouchTask1.DEBUG) {
            Log.d(TAG, "interrupt()");
        }
        mIsCancelled.set(true);
        __interrupt();
    }

    private void __listenAsyn(final int expectDataLen) {
        mTask = new Thread() {
            public void run() {
                if (__IEsptouchTask1.DEBUG) {
                    Log.d(TAG, "__listenAsyn() start");
                }
                long startTimestamp = System.currentTimeMillis();
//                byte[] apSsidAndPassword = ByteUtil1.getBytesByString(mApSsid
//                        + mApPassword);
                byte expectOneByte = (byte) (mApSsid.length + mApPassword.length + 9);
                if (__IEsptouchTask1.DEBUG) {
                    Log.i(TAG, "expectOneByte: " + (0 + expectOneByte));
                }
                byte receiveOneByte = -1;
                byte[] receiveBytes = null;
                while (mEsptouchResultList.size() < mParameter
                        .getExpectTaskResultCount() && !mIsInterrupt) {
                    receiveBytes = mSocketServer
                            .receiveSpecLenBytes(expectDataLen);
                    if (receiveBytes != null) {
                        receiveOneByte = receiveBytes[0];
                    } else {
                        receiveOneByte = -1;
                    }
                    if (receiveOneByte == expectOneByte) {
                        if (__IEsptouchTask1.DEBUG) {
                            Log.i(TAG, "receive correct broadcast");
                        }
                        // change the socket's timeout
                        long consume = System.currentTimeMillis()
                                - startTimestamp;
                        int timeout = (int) (mParameter
                                .getWaitUdpTotalMillisecond() - consume);
                        if (timeout < 0) {
                            if (__IEsptouchTask1.DEBUG) {
                                Log.i(TAG, "esptouch timeout");
                            }
                            break;
                        } else {
                            if (__IEsptouchTask1.DEBUG) {
                                Log.i(TAG, "mSocketServer's new timeout is "
                                        + timeout + " milliseconds");
                            }
                            mSocketServer.setSoTimeout(timeout);
                            if (__IEsptouchTask1.DEBUG) {
                                Log.i(TAG, "receive correct broadcast");
                            }
                            if (receiveBytes != null) {
                                String bssid = ByteUtil1.parseBssid(
                                        receiveBytes,
                                        mParameter.getEsptouchResultOneLen(),
                                        mParameter.getEsptouchResultMacLen());
                                InetAddress inetAddress = EspNetUtil1
                                        .parseInetAddr(
                                                receiveBytes,
                                                mParameter
                                                        .getEsptouchResultOneLen()
                                                        + mParameter
                                                        .getEsptouchResultMacLen(),
                                                mParameter
                                                        .getEsptouchResultIpLen());
                                __putEsptouchResult(true, bssid, inetAddress);
                            }
                        }
                    } else {
                        if (__IEsptouchTask1.DEBUG) {
                            Log.i(TAG, "receive rubbish message, just ignore");
                        }
                    }
                }
                mIsSuc = mEsptouchResultList.size() >= mParameter
                        .getExpectTaskResultCount();
                __EsptouchTask1.this.__interrupt();
                if (__IEsptouchTask1.DEBUG) {
                    Log.d(TAG, "__listenAsyn() finish");
                }
            }
        };
        mTask.start();
    }

    private boolean __execute(IEsptouchGenerator1 generator) {

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        long lastTime = currentTime - mParameter.getTimeoutTotalCodeMillisecond();

        byte[][] gcBytes2 = generator.getGCBytes2();
        byte[][] dcBytes2 = generator.getDCBytes2();

        int index = 0;
        while (!mIsInterrupt) {
            if (currentTime - lastTime >= mParameter.getTimeoutTotalCodeMillisecond()) {
                if (__IEsptouchTask1.DEBUG) {
                    Log.d(TAG, "send gc code ");
                }
                // send guide code
                while (!mIsInterrupt
                        && System.currentTimeMillis() - currentTime < mParameter
                        .getTimeoutGuideCodeMillisecond()) {
                    mSocketClient.sendData(gcBytes2,
                            mParameter.getTargetHostname(),
                            mParameter.getTargetPort(),
                            mParameter.getIntervalGuideCodeMillisecond());
                    // check whether the udp is send enough time
                    if (System.currentTimeMillis() - startTime > mParameter.getWaitUdpSendingMillisecond()) {
                        break;
                    }
                }
                lastTime = currentTime;
            } else {
                mSocketClient.sendData(dcBytes2, index, ONE_DATA_LEN,
                        mParameter.getTargetHostname(),
                        mParameter.getTargetPort(),
                        mParameter.getIntervalDataCodeMillisecond());
                index = (index + ONE_DATA_LEN) % dcBytes2.length;
            }
            currentTime = System.currentTimeMillis();
            // check whether the udp is send enough time
            if (currentTime - startTime > mParameter.getWaitUdpSendingMillisecond()) {
                break;
            }
        }

        return mIsSuc;
    }

    private void __checkTaskValid() {
        // !!!NOTE: the esptouch task could be executed only once
        if (this.mIsExecuted) {
            throw new IllegalStateException(
                    "the Esptouch task could be executed only once");
        }
        this.mIsExecuted = true;
    }

    @Override
    public IEsptouchResult1 executeForResult() throws RuntimeException {
        return executeForResults(1).get(0);
    }

    @Override
    public boolean isCancelled() {
        return this.mIsCancelled.get();
    }

    @Override
    public List<IEsptouchResult1> executeForResults(int expectTaskResultCount)
            throws RuntimeException {
        __checkTaskValid();

        mParameter.setExpectTaskResultCount(expectTaskResultCount);

        if (__IEsptouchTask1.DEBUG) {
            Log.d(TAG, "execute()");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException(
                    "Don't call the esptouch Task at Main(UI) thread directly.");
        }
        InetAddress localInetAddress = EspNetUtil1.getLocalInetAddress(mContext);
        if (__IEsptouchTask1.DEBUG) {
            Log.i(TAG, "localInetAddress: " + localInetAddress);
        }
        // generator the esptouch byte[][] to be transformed, which will cost
        // some time(maybe a bit much)
        IEsptouchGenerator1 generator = new EsptouchGenerator1(mApSsid, mApBssid,
                mApPassword, localInetAddress, mIsSsidHidden);
        // listen the esptouch result asyn
        __listenAsyn(mParameter.getEsptouchResultTotalLen());
        boolean isSuc = false;
        for (int i = 0; i < mParameter.getTotalRepeatTime(); i++) {
            isSuc = __execute(generator);
            if (isSuc) {
                return __getEsptouchResultList();
            }
        }

        if (!mIsInterrupt) {
            // wait the udp response without sending udp broadcast
            try {
                Thread.sleep(mParameter.getWaitUdpReceivingMillisecond());
            } catch (InterruptedException e) {
                // receive the udp broadcast or the user interrupt the task
                if (this.mIsSuc) {
                    return __getEsptouchResultList();
                } else {
                    this.__interrupt();
                    return __getEsptouchResultList();
                }
            }
            this.__interrupt();
        }

        return __getEsptouchResultList();
    }

    @Override
    public void setEsptouchListener(IEsptouchListener1 esptouchListener) {
        mEsptouchListener = esptouchListener;
    }

}
