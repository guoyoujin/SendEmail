package com.github.jdsjlzx.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

/**
 * 在此写用途
 *
 * @FileName: com.github.jdsjlzx.recyclerview.RevOverScrollStyle.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-05-19 09:54
 * @version: V1.0 <描述当前版本功能>
 */

public abstract class RevOverScrollStyle extends OverScrollDelegate.OverScrollStyle {

    final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    final int backgroundColor;// = 0xff282b2d;
    final int textColor;// = 0xff6e7476;
    final float textCenterY;
    final Rect bounds = new Rect();

    public RevOverScrollStyle() {
        this(0xff282b2d, 0xff6e7476, 14, 32);

    }

    public RevOverScrollStyle(int backgroundColor, int textColor, int textSizeDp, int textCenerYDp) {
        super();
        this.paint.density = SYSTEM_DENSITY;
        this.paint.setTextSize(dp2px(textSizeDp));
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textCenterY = dp2px(textCenerYDp) ;
    }

    @Override
    public void drawOverScrollTop(float offsetY, Canvas canvas, View view) {
        Log.d("====",offsetY+"");
        bounds.left = view.getScrollX();
        bounds.right = bounds.left + view.getWidth();
        bounds.top = 0;
        bounds.bottom = Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
        paint.setColor(backgroundColor);
        canvas.drawRect(bounds, paint);
        paint.setColor(textColor);
        canvas.clipRect(bounds);
        if (view != null) {
            String text = formatUrlHost();
            if (text != null) {
                canvas.drawText(text, bounds.exactCenterX(), textCenterY, paint);
            }
        }
    }

    @Override
    public void drawOverScrollBottom(float offsetY, Canvas canvas, View view) {
        bounds.left = view.getScrollX();
        bounds.right = bounds.left + view.getWidth();
        bounds.bottom = getOverScrollViewCanvasBottom(view);
        // view.getHeight() + view.getScrollY();
        bounds.top = bounds.bottom + Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
        paint.setColor(backgroundColor);
        canvas.drawRect(bounds, paint);
        paint.setColor(textColor);
        canvas.clipRect(bounds);
        if (view != null) {
            String text = formatUrlHost();
            if (text != null) {
                canvas.drawText(text, bounds.exactCenterX(), textCenterY, paint);
            }
        }
    }

    //"Provided by " + new URL(url).getHost()
    // String text = "网页由 " + new URL(url).getHost() + " 提供";
    public abstract String formatUrlHost();
}