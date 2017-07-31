package com.trycatch.sendemail.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.trycatch.sendemail.R;
import com.trycatch.sendemail.checkbox.SmoothCheckBox;
import com.trycatch.sendemail.vo.UserEmail;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.adapter.ExcelAdapter.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-28 18:34
 * @version: V1.0 <描述当前版本功能>
 */

public class ExcelAdapter extends ListBaseAdapter<UserEmail> {
    private Context context;
    public ExcelAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.excel_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        UserEmail userEmail = mDataList.get(position);
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvInfo = holder.getView(R.id.tvInfo);
        TextView tv_address = holder.getView(R.id.tv_address);
        ImageView imgEmail = holder.getView(R.id.imgEmail);
        if(userEmail.getSendState()==1){
            imgEmail.setImageResource(R.mipmap.send_email);
        }else{
            imgEmail.setImageResource(R.mipmap.no_send_email);
        }
        SmoothCheckBox chBoxOrderReson = holder.getView(R.id.chBoxOrderReson);
        tvTitle.setText("姓名："+userEmail.getFirstName()+userEmail.getLastName());
        tvInfo.setText("邮箱："+userEmail.getEmail());
        tv_address.setText("国家："+userEmail.getCountry());
        chBoxOrderReson.setChecked(userEmail.isChecked(),false);
        chBoxOrderReson.setClickable(false);
    }
}
