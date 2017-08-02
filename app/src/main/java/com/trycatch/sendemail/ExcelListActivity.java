package com.trycatch.sendemail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LRecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;
import com.trycatch.sendemail.adapter.ExcelAdapter;
import com.trycatch.sendemail.vo.UserEmail;

import org.apache.poi.ss.usermodel.Workbook;
import org.reactivestreams.Publisher;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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
    @BindView(R.id.floatActionbutton)
    FloatingActionButton floatActionbutton;
    AlertDialog.Builder builder = null;
    AlertDialog dialog = null;
    private Workbook workBook;
    private int changeLength = 0;
    private String title = "";
    private String message = "";
    private String fromEmail = "";
    private String copyEmail = "";
    private String fromEmailPassword = "";
    private TSnackbar tSnackbar;
    private Disposable disSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_list);
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && android.os.Build.VERSION.SDK_INT<=Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ButterKnife.bind(this);
        if("".equals(PreferenceUtils.getPrefString(this,PreKey.EMAIL_CONTENT,""))){
            PreferenceUtils.setPrefString(this,PreKey.EMAIL_CONTENT,"<div id=\":op\" class=\"ii gt adP adO\"><div id=\":oo\" class=\"a3s aXjCH m15d832001e4f97b0\"><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">Dear&nbsp;Customer,</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">&nbsp;</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><font face=\"宋体\"><span style=\"font-size:13.3333px\">We get that you are in the market for replica watches</span></font><span style=\"font-family:宋体;font-size:10pt\">.&nbsp;We are very pleased to meet you.</span><span style=\"font-family:宋体;font-size:10pt\">&nbsp;</span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">Welcome to visit our watch site : </span><span style=\"font-family:宋体;color:rgb(0,0,0);font-size:10.0000pt\"><a href=\"http://www.ukcheapshop.co.uk\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=zh-CN&amp;q=http://www.ukcheapshop.co.uk&amp;source=gmail&amp;ust=1501747087562000&amp;usg=AFQjCNHyHKHug96YWAiPbqfbxHbAyu152A\">www.ukcheapshop.co.uk</a></span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><br></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:13.3333px\">We offer AAA+ best replica watches, include rolex, omega, cartier, etc. 1:1 top quality, free shipping with 5-10 days can be delivered.</span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">&nbsp;</span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">To attract your attention and increase the sales performance, we will offer 6% discount coupon to you.</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">please enter Redemption Code when you on checkout payment step</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">Redemption Code:&nbsp;6disc</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">the code will give you 6% discount for all your order.</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">&nbsp;</span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">For help with any of our online services, please email to <a href=\"mailto:watchesonline@foxmail.com\" target=\"_blank\">watchesonline@foxmail.com</a></span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\"><br></span><span style=\"font-family:宋体;font-size:10pt\">Sincerely,</span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:10pt\">Amy<br></span><span style=\"font-family:宋体;font-size:10pt\"><u></u><u></u></span></p></div><div class=\"yj6qo\"></div></div>");
        }
        if("".equals(PreferenceUtils.getPrefString(this,PreKey.EMAIL_SUBJECT,""))){
            PreferenceUtils.setPrefString(this,PreKey.EMAIL_SUBJECT,"Message from www.ukcheapshop.co.uk");
        }
        if("".equals(PreferenceUtils.getPrefString(this,PreKey.FROM_EMAIL,""))){
            PreferenceUtils.setPrefString(this,PreKey.FROM_EMAIL,"watchesonline@foxmail.com");
        }
        initActionBar();
        initSnackBar();
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
                initData();
            }
        });
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mExcelAdapter.getDataList().get(position).setChecked(!mExcelAdapter.getDataList().get(position).isChecked());
                if(mExcelAdapter.getDataList().get(position).isChecked()){
                    changeLength++;
                }else{
                    changeLength--;
                }
                allChange.setText(String.format(allChange.getText().toString().substring(0,2)+"(%s)",changeLength));
                mLRecyclerViewAdapter.notifyDataSetChanged();
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
    
    
    public void initDialog(){
        message = PreferenceUtils.getPrefString(this,PreKey.EMAIL_CONTENT,"");
        fromEmail = PreferenceUtils.getPrefString(this,PreKey.FROM_EMAIL,"");
        copyEmail = PreferenceUtils.getPrefString(this,PreKey.COPY_EMAIL,"");
        fromEmailPassword = PreferenceUtils.getPrefString(this,PreKey.FROM_EMAIL_PASSWORD,"");
        title = PreferenceUtils.getPrefString(this,PreKey.EMAIL_SUBJECT,"");
    }

    public void setPreData(){
        PreferenceUtils.setPrefString(this,PreKey.EMAIL_CONTENT,message);
        PreferenceUtils.setPrefString(this,PreKey.FROM_EMAIL,fromEmail);
        PreferenceUtils.setPrefString(this,PreKey.COPY_EMAIL,copyEmail);
        PreferenceUtils.setPrefString(this,PreKey.FROM_EMAIL_PASSWORD,fromEmailPassword);
        PreferenceUtils.setPrefString(this,PreKey.EMAIL_SUBJECT,title);
    }
    public void initActionClick(){
        initDialog();
        builder = new AlertDialog.Builder(ExcelListActivity.this);
        View view = LayoutInflater.from(ExcelListActivity.this).inflate(R.layout.view_enter_edit, null);
        builder.setView(view);
        dialog = builder.create();
        final ViewHolderSms holder = new ViewHolderSms(view);
        holder.make_sms_title.setText(ExcelListActivity.this.getResources().getString(R.string.app_name));
        if (message==null){
            message = "";
        }
        holder.editEmail.setText(fromEmail);
        holder.editEmail.setSelection(fromEmail.length());
        holder.editCopyEmail.setText(copyEmail);
        holder.editCopyEmail.setSelection(copyEmail.length());
        holder.editPassword.setText(fromEmailPassword);
        holder.editPassword.setSelection(fromEmailPassword.length());
        holder.editTextSmsValue.setText(message);
        holder.editTextSmsValue.setSelection(message.length());
        holder.dialog_btn_ok.setEnabled(!message.toString().matches(""));
        holder.editEmailSubject.setText(title);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        holder.editTextSmsValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    
            }
    
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
    
            }
    
            @Override
            public void afterTextChanged(Editable s) {
                holder.dialog_btn_ok.setEnabled(!s.toString().matches(""));
            }
        });
        holder.dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromEmail = holder.editEmail.getText().toString();
                copyEmail = holder.editCopyEmail.getText().toString();
                fromEmailPassword = holder.editPassword.getText().toString();
                message = holder.editTextSmsValue.getText().toString();
                title = holder.editEmailSubject.getText().toString();
                setPreData();
                sendEmail(mExcelAdapter.getDataList());
                dialog.dismiss();
                
            }
        });
        holder.dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    static class ViewHolderSms {
        @BindView(R.id.make_sms_title)
        TextView make_sms_title;
        
        @BindView(R.id.editTextSmsValue)
        EditText editTextSmsValue;
        
        @BindView(R.id.editEmailSubject)
        EditText editEmailSubject;
        
        @BindView(R.id.editEmail)
        EditText editEmail;
        
        @BindView(R.id.editPassword)
        EditText editPassword;
        
        @BindView(R.id.editCopyEmail)
        EditText editCopyEmail;
        
        @BindView(R.id.dialog_btn_cancel)
        Button dialog_btn_cancel;
        
        @BindView(R.id.dialog_btn_ok)
        Button dialog_btn_ok;
        ViewHolderSms(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @OnClick(R.id.allChange)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allChange:
                if(allChange.getText().toString().substring(0,2).equals("全选")){
                    changeChecked(true);
                    allChange.setText(String.format("反选(%s)",changeLength));
                    return ;
                }
                if(allChange.getText().toString().substring(0,2).equals("反选")){
                    changeChecked(false);
                    allChange.setText(String.format("全选(%s)",changeLength));
                    return ;
                }
                break;
        }
    }
    
    @OnClick(R.id.floatActionbutton)
    public void actionButtonClick(){
        initActionClick();
    }

    public void changeChecked(boolean flag){
        if(mExcelAdapter.getDataList()!=null){
            for (int i=0;i<mExcelAdapter.getDataList().size();i++){
                mExcelAdapter.getDataList().get(i).setChecked(flag);
            }
            mLRecyclerViewAdapter.notifyDataSetChanged();
            if(flag){
                changeLength = mExcelAdapter.getDataList().size();
            }else{
                changeLength = 0;
            }
        }
    }
    
    public TSnackbar initSnackBar(){
        tSnackbar = TSnackbar.make((ViewGroup) findViewById(android.R.id.content).getRootView(), "正在登录，请稍后...", TSnackbar.LENGTH_INDEFINITE, TSnackbar.APPEAR_FROM_BOTTOM_TO_TOP);
        tSnackbar.addIcon(R.mipmap.ic_launcher,100,100);
        tSnackbar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!disSendEmail.isDisposed()){
                    disSendEmail.dispose();
                }
            }
        });
        tSnackbar.setPromptThemBackground(Prompt.SUCCESS);
        tSnackbar.addIconProgressLoading(0,true,false);
        return tSnackbar;
    }
    
    
    public boolean senEmailHtml(UserEmail userEmail){
        boolean flag = false;
        if(ExcelUtil.checkEmail(userEmail.getEmail())){
            if (userEmail.isChecked() && userEmail.getSendState()==0){
                MyAuthenticator authenticator = null;
                authenticator = new MyAuthenticator(fromEmail, fromEmailPassword);
                Session sendMailSession = Session.getDefaultInstance(getProperties(),authenticator);
                try {
                    // 根据session创建一个邮件消息
                    Message mailMessage = new MimeMessage(sendMailSession);
                    // 创建邮件发送者地址
                    Address from = new InternetAddress(fromEmail);
                    // 设置邮件消息的发送者
                    mailMessage.setFrom(from);
                    // 创建邮件的接收者地址，并设置到邮件消息中
                    Address to = new InternetAddress(userEmail.getEmail());
                    // Message.RecipientType.TO属性表示接收者的类型为TO
                    mailMessage.setRecipient(Message.RecipientType.TO, to);
                    if("".equals(copyEmail) || copyEmail == null){
                    }else{
                        mailMessage.setRecipient(Message.RecipientType.CC,new InternetAddress((copyEmail))); 
                    }
                    // 设置邮件消息的主题
                    mailMessage.setSubject(title);
                    // 设置邮件消息发送的时间
                    mailMessage.setSentDate(new Date());
                    // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
                    Multipart mainPart = new MimeMultipart();
                    // 创建一个包含HTML内容的MimeBodyPart
                    BodyPart html = new MimeBodyPart();
                    // 设置HTML内容
                    html.setContent(message, "text/html; charset=utf-8");
                    mainPart.addBodyPart(html);
                    // 将MiniMultipart对象设置为邮件内容
                    mailMessage.setContent(mainPart);
                    // 发送邮件
                    Transport.send(mailMessage);
                    flag = true;
                } catch (MessagingException ex) {
                    flag = false;
                    ex.printStackTrace();
                }
            }else{
                flag = false;
                Log.d(TAG,"已发送");
            }
        }else{
            flag = false;
            Log.d(TAG,"邮箱非法");
        }
        return flag;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smpt.starttls.enable", "true");
        p.put("mail.smtp.host", getMailServerHost());
        p.put("mail.smtp.port", getMailServerPort());
        return p;
    }
    public String getMailServerHost() {
        String str = "smtp.qq.com";
        if(str.endsWith("@gmail.com")){
            str = "smtp.gmail.c";
        }
        if(str.endsWith("@qq.com")){
            str="smtp.qq.com";
        }
        if(str.endsWith("@163.com")){
            str="smtp.163.com";
        }
        return str;
    }
    public String getMailServerPort() {
        String str = "587";
        if(str.endsWith("@gmail.com")){
            str = "587";
        }
        if(str.endsWith("@qq.com")){
            str="587";
        }
        if(str.endsWith("@163.com")){
            str="994";
        }
        return str;
    }
    private int sendCount = 0;
    public void sendEmail(List<UserEmail> list){
        Log.d(TAG, "List===>>>"+list.toString());
        sendCount = 0;
        tSnackbar.setText(String.format("正在发送%s",sendCount));
        tSnackbar.show();
        disSendEmail =  Flowable.just(list).flatMap(new Function<List<UserEmail>, Publisher<UserEmail>>() {
            @Override
            public Publisher<UserEmail> apply(@NonNull List<UserEmail> userEmails) throws Exception {
                Log.d(TAG, "userEmails===apply>>>"+userEmails.toString());
                return Flowable.fromIterable(userEmails);
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread(),false,100)
        .flatMap(new Function<UserEmail, Publisher<UserEmail>>() {
            @Override
            public Publisher<UserEmail> apply(@NonNull UserEmail userEmail) throws Exception {
                Log.d(TAG, "userEmail===>>>"+userEmail);
                if(senEmailHtml(userEmail)){
                    sendCount++;
                    ExcelUtil.setExceLUserMail(workBook,userEmail,file_path);
                    SystemClock.sleep(5000); 
                }
                return Flowable.just(userEmail);
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread(),false,100)
        .subscribe(new Consumer<UserEmail>() {
            @Override
            public void accept(@NonNull UserEmail userEmail) throws Exception {
                Log.d(TAG, "userEmail===>>>" + String.format("已发送%s", sendCount));
                tSnackbar.setText(String.format("正在发送%s", sendCount));
                tSnackbar.show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                tSnackbar.dismiss();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                tSnackbar.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mpDialog!=null && mpDialog.isShowing()){
            mpDialog.dismiss();
        }
    }
}
