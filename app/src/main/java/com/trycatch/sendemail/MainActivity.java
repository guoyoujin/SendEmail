package com.trycatch.sendemail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.trycatch.sendemail.date.FinalDate;
import com.trycatch.sendemail.vo.FileMode;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {
    private ArrayList<FileMode> list;
    protected ProgressDialog mpDialog = null;
    protected static final String TAG = MainActivity.class.getSimpleName();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    protected ListView lv;
    private static final int REQUEST_EXTERNAL_STORAGE = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        ButterKnife.bind(this);
        initPermision();
        
    }

//    @OnClick(R.id.addExcel)
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.addExcel:
//                InActivity.startActivity(MainActivity.this);
//                break;
//        }
//    }

    public  void initPermision(){
        int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }else{
            initData();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,requestCode+"====");
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    
    
    public void initData(){
        Observable.just(Environment.getExternalStorageDirectory().listFiles())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread()).map(new Function<File[], ArrayList<FileMode>>() {
            @Override
            public ArrayList<FileMode> apply(@io.reactivex.annotations.NonNull File[] files) throws Exception {
                int len = files.length;
                ArrayList<FileMode> list_l = new ArrayList<FileMode>();
                for (int i = 0; i < len; i++) {
                    File file = files[i];
                    if (file.toString().contains(FinalDate.FFILE_TYPE)) {
                        FileMode fileMode = new FileMode();
                        fileMode.setParent(file.getParent());
                        fileMode.setName(file.getName());
                        fileMode.setPath(file.toString());
                        fileMode.setType("0");
                        list_l.add(fileMode);
                    }
                }
                return list_l;
            }
        }).subscribe(new Observer<ArrayList<FileMode>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                Log.d(TAG,"=onSubscribe=");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull ArrayList<FileMode> fileModes) {
                Log.d(TAG,fileModes.toString());
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable throwable) {
                Log.d(TAG,"=onError="+throwable.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"=onError=");
            }
        });
    }
    private void showProgressDialog(String msg) {
        mpDialog = new ProgressDialog(this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
        mpDialog.setMessage(msg);
        mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
        mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
        mpDialog.show();
    }

}
