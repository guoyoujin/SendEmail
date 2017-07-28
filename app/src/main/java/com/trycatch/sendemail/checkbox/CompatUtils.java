package com.trycatch.sendemail.checkbox;

import android.content.Context;

/**
 * 在此写用途
 *
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2016-08-29 14:00
 * @version: V1.0 <描述当前版本功能>
 */

public class CompatUtils {
    private static final String TAG = "CompatUtils";
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}