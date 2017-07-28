package com.trycatch.sendemail.adapter;

import android.content.Context;
import android.widget.TextView;

import com.trycatch.sendemail.R;
import com.trycatch.sendemail.vo.UserEmail;

import static com.trycatch.sendemail.R.id.fileName;

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
        tvTitle.setText("用户名："+userEmail.getFirstName()+" "+ userEmail.getLastName()+ "       邮箱："+userEmail.getEmail());
        tvInfo.setText("国家："+userEmail.getCountry()+ "    地址："+userEmail.getCountry());
    }
}
