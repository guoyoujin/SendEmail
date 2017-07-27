package com.trycatch.sendemail.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.helper.UserEmailDb.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-26 17:51
 * @version: V1.0 <描述当前版本功能>
 */

public class UserEmailDb extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "user_email.db";
    private static int DATABASE_VERSION = 1;
    private String table = null;

    public UserEmailDb(Context context, String table) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.table = table;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + table + " ( first_name text , " + " last_name text , " + " email text " + ", country text" + ", addrerss  text , send_state int);";
        Log.d("===","===>>>"+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
