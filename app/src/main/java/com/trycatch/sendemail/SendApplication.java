package com.trycatch.sendemail;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.trycatch.sendemail.helper.BookDB;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.SendApplication.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-25 11:43
 * @version: V1.0 <描述当前版本功能>
 */

public class SendApplication extends MultiDexApplication {
    public static BookDB bookDB;
    private static Context context;
    private static Application mApplication;
    public static synchronized Application getInstance() {
        return mApplication;
    }
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        context = getApplicationContext();
        initDateBase();
    }
       
    private static void initDateBase() {
    }

}

