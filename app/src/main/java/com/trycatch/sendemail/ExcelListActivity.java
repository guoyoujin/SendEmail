package com.trycatch.sendemail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LRecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.trycatch.sendemail.adapter.ExcelAdapter;
import com.trycatch.sendemail.vo.UserEmail;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ExcelListActivity extends AppCompatActivity {
    private static String EXCEL_FILE_PATH="EXCEL_FILE_PATH";
    private String file_path="";
    private ProgressDialog mpDialog = null;
    protected static final String TAG = ExcelListActivity.class.getSimpleName();
    private ExcelAdapter mExcelAdapter= null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    @BindView(R.id.exceLRecyclerView)
    LRecyclerView exceLRecyclerView;
    @BindView(R.id.allChange)
    TextView allChange;
    
    
    private Workbook workBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_list);
        ButterKnife.bind(this);
        initActionBar();
        getIntentValue();
        initData();
        initRecyclerView();
    }

    private void initActionBar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        } 
    }

    public static void startActivity(Context context,String filePath){
        Intent intent = new Intent(context,ExcelListActivity.class);
        intent.putExtra(EXCEL_FILE_PATH,filePath);
        context.startActivity(intent);
    }

    public void getIntentValue(){
        file_path = getIntent().getStringExtra(EXCEL_FILE_PATH);
    }

    private void showProgressDialog(String msg) {
        mpDialog = new ProgressDialog(this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
        mpDialog.setMessage(msg);
        mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
        mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
        mpDialog.show();
    }
    
    public  void initData(){
        Observable.just(file_path).map(new Function<String, List<UserEmail>>() {
            @Override
            public List<UserEmail> apply(@io.reactivex.annotations.NonNull String path) throws Exception {
                workBook = ExcelUtil.getWorkBook(path);
                return ExcelUtil.redWorkBookGetData(workBook);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread(),false,100)
        .subscribe(new Observer<List<UserEmail>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                Log.d(TAG,"=onSubscribe=");
                showProgressDialog("正在读取文件,请稍后.....");
            }
            @Override
            public void onNext(@io.reactivex.annotations.NonNull List<UserEmail> list_user_email) {
                Log.d(TAG,list_user_email.toString());
                mExcelAdapter.addAll(list_user_email);
                exceLRecyclerView.refreshComplete(10);
                dismissDialog();
                
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
    public void dismissDialog(){
        if(mpDialog!=null&&mpDialog.isShowing()){
            mpDialog.dismiss();
        }
    }
    
    public void initRecyclerView(){
        mExcelAdapter = new ExcelAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mExcelAdapter);
        exceLRecyclerView.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();
        exceLRecyclerView.addItemDecoration(divider);
        exceLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exceLRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        exceLRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        exceLRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        exceLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
               
            }
        });
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


}
