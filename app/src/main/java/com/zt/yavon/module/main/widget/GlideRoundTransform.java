package com.zt.yavon.module.main.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by hp on 2018/1/17.
 */

public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;
    private static int COLOR_BK = 0;
    private float bordWidth = 0;
    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int radiusDP) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * radiusDP;
    }
    public GlideRoundTransform(Context context, int radiusDP,int bkColor) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * radiusDP;
        this.COLOR_BK = bkColor;
        this.bordWidth = Resources.getSystem().getDisplayMetrics().density * 1;
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
//        LogUtil.d("===============width:"+source.getWidth()+",height:"+source.getHeight());
        if(COLOR_BK != 0){
            RectF rectF = new RectF(bordWidth/2f, bordWidth/2f, source.getWidth()-bordWidth/2f, source.getHeight()-bordWidth/2f);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setColor(COLOR_BK);
            paint2.setStyle(Paint.Style.STROKE);
//            paint2.setStrokeCap(Paint.Cap.ROUND);
            paint2.setStrokeWidth(bordWidth);
            canvas.drawRoundRect(rectF, radius, radius, paint2);
        }else{
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        return result;
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
