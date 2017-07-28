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
import android.support.v7.widget.LRecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.trycatch.sendemail.adapter.DataAdapter;
import com.trycatch.sendemail.adapter.FileDataAdapter;
import com.trycatch.sendemail.date.FinalDate;
import com.trycatch.sendemail.vo.FileMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    protected ProgressDialog mpDialog = null;
    protected static final String TAG = MainActivity.class.getSimpleName();
    private DataAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    protected ListView lv;
    private static final int REQUEST_EXTERNAL_STORAGE = 111;

    @BindView(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;
    
    @BindView(R.id.mRecyclerView)
    LRecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPermision();
        initRecycleView();
    }
    
    public void initRecycleView(){
        mDataAdapter = new DataAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        lRecyclerView.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();
        lRecyclerView.addItemDecoration(divider);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                initPermision();
            }
        });
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                initMRecycleView(new File(mDataAdapter.getDataList().get(position).getPath()));
                lRecyclerView.setVisibility(View.GONE);
                
            }
        });
    }

    private FileDataAdapter mFileDataAdapter = null;
    private LRecyclerViewAdapter mFileLRecyclerViewAdapter = null;
    
    
    
    
    public void initList(File file){
        Observable.just(file).map(new Function<File, List<FileMode>>() {
            @Override
            public List<FileMode> apply(@io.reactivex.annotations.NonNull File file) throws Exception {
                List<FileMode> list_ll = new ArrayList<FileMode>();
                File[] fl = file.listFiles();
                int lg = fl.length;
                for(int i = 0; i < lg; i++){
                    File f2 = fl[i];
                    if (f2.isFile()) {
                        if (f2.toString().contains(FinalDate.FFILE_TYPE) || f2.toString().contains(FinalDate.FFILE_TYPE2)) {
                            FileMode fileMode = new FileMode();
                            fileMode.setName(f2.getName());
                            fileMode.setPath(f2.toString());
                            fileMode.setType("1");
                            list_ll.add(fileMode);
                        }
                    }
                }
                return list_ll;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<FileMode>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                Log.d(TAG,"=onSubscribe=");
                showProgressDialog("正在读取文件,请稍后.....");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull List<FileMode> fileModes) {
                Log.d(TAG,fileModes.toString());
                mFileDataAdapter.addAll(fileModes);
                mRecyclerView.refreshComplete(10);
                final View header = LayoutInflater.from(MainActivity.this).inflate(R.layout.back_superior,(ViewGroup)findViewById(android.R.id.content), false);
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                mFileLRecyclerViewAdapter.addHeaderView(header);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable throwable) {
                Log.d(TAG,"=onError="+throwable.toString());
                if(mpDialog != null && mpDialog.isShowing()){
                    mpDialog.dismiss();
                }
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"=onComplete=");
                if(mpDialog != null && mpDialog.isShowing()){
                    mpDialog.dismiss();
                }
            }
        }); 
    }

    public void initMRecycleView(final File file){
        mFileDataAdapter = new FileDataAdapter(this);
        mFileLRecyclerViewAdapter = new LRecyclerViewAdapter(mFileDataAdapter);
        mRecyclerView.setAdapter(mFileLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initList(file);
        mRecyclerView.setVisibility(View.VISIBLE);
        mFileLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "==position===="+position);
                List<FileMode> list =new ArrayList<FileMode>();
                for (int i=0; i<mFileDataAdapter.getDataList().size(); i++){
                    FileMode fileMode = mFileDataAdapter.getDataList().get(i);
                   if(i == position){
                       fileMode.setChecked(true);
                   }else{
                       fileMode.setChecked(false);
                   }
                    list.add(fileMode);
                }
                mFileDataAdapter.clear();
                mFileDataAdapter.addAll(list);
                lRecyclerView.refreshComplete(10);
                mFileLRecyclerViewAdapter.notifyDataSetChanged();
                Log.d(TAG,"=mFileDataAdapter.getDataList()==="+mFileDataAdapter.getDataList().toString());
               
            }
        });
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFileLRecyclerViewAdapter.removeHeaderView();
                mFileDataAdapter.clear();
                mFileLRecyclerViewAdapter.notifyDataSetChanged();
                mFileLRecyclerViewAdapter.notifyAll();
                initList(file);
            }
        });
    }

    @OnClick(R.id.addExcel)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addExcel:
                InActivity.startActivity(MainActivity.this);
                break;
        }
    }

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

    public void printAllFile(File file,List<FileMode> list_l) {
        if (file.isFile()) {
            if (file.toString().contains(FinalDate.FFILE_TYPE) || file.toString().contains(FinalDate.FFILE_TYPE2)) {
                FileMode fileMode = new FileMode();
                fileMode.setName(file.getParentFile().getName());
                fileMode.setPath(file.getParentFile().toString());
                fileMode.setType("0");
                list_l.add(fileMode);
            }
        }
        if (file.isDirectory()) {
            if (file!=null) {
                File[] f1 = file.listFiles();
                if (f1==null) {
                    return;
                }
                int len = f1.length;
                for (int i = 0; i < len; i++) {
                    printAllFile(f1[i],list_l);
                }
            }
        }

    }
    
    public int fileCount(File file){
        int lg = 0;
        if(file.isDirectory()){
            File[] f2 = file.listFiles();
            if (f2 == null) {
                return lg;
            }
            int len = f2.length;
            for (int i = 0; i < len; i++) {
                if(f2[i].isFile() && (f2[i].toString().contains(FinalDate.FFILE_TYPE) || f2[i].toString().contains(FinalDate.FFILE_TYPE2))){
                    Log.d(TAG,"出现文件");
                    lg++;
                }
            }
        }else{
            return lg;
        }
        return lg;
    }
    
    public void initData(){
        Observable.just(Environment.getExternalStorageDirectory()).delay(1, TimeUnit.SECONDS)
        .map(new Function<File, List<FileMode>>() {
            @Override
            public List<FileMode> apply(@io.reactivex.annotations.NonNull File file) throws Exception {
                List<FileMode> list_l = new ArrayList<FileMode>();
                printAllFile(file,list_l);
                
                //警告，假如有重复数据的话就需要用到去重功能
                HashSet h  =   new  HashSet<FileMode>(list_l);
                list_l.clear();
                list_l.addAll(h);
                
                Log.d(TAG,"=list_l="+list_l.toString());
                if(list_l != null && list_l.size() > 0){
                    int length = list_l.size();
                    for (int i = 0 ;i<length;i++){
                        list_l.get(i).setCount(fileCount(new File(list_l.get(i).getPath())));
                    }
                }
                return list_l;
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<FileMode>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                Log.d(TAG,"=onSubscribe=");
                showProgressDialog("正在读取文件,请稍后.....");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull List<FileMode> fileModes) {
                Log.d(TAG,fileModes.toString());
                mDataAdapter.addAll(fileModes);
                lRecyclerView.refreshComplete(10);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable throwable) {
                Log.d(TAG,"=onError="+throwable.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"=onComplete=");
                if(mpDialog != null && mpDialog.isShowing()){
                    mpDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mpDialog = null;
    }

    private void showProgressDialog(String msg) {
        mpDialog = new ProgressDialog(this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
        mpDialog.setMessage(msg);
        mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
        mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
        mpDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(mRecyclerView !=null && mRecyclerView.getVisibility() == View.VISIBLE){
            mRecyclerView.setVisibility(View.GONE);
            lRecyclerView.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }
}
