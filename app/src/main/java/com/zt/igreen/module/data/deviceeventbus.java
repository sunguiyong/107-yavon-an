package com.zt.igreen.module.data;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class deviceeventbus {
    private String code;//是否成功
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public deviceeventbus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
