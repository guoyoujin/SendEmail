package com.trycatch.sendemail.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.trycatch.sendemail.R;
import com.trycatch.sendemail.checkbox.SmoothCheckBox;
import com.trycatch.sendemail.vo.FileMode;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.adapter.FileDataAdapter.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-28 14:11
 * @version: V1.0 <描述当前版本功能>
 */

public class FileDataAdapter extends ListBaseAdapter<FileMode> {
    private Context context;
    public FileDataAdapter(Context context) {
        super(context);
        this.context = context;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.list_item;
    }
    
    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FileMode fileMode = mDataList.get(position);
        ImageView fileImage = holder.getView(R.id.fileImage);
        TextView fileName = holder.getView(R.id.fileName);
        TextView fileCount = holder.getView(R.id.fileCount);
        SmoothCheckBox chBoxOrderReson = holder.getView(R.id.chBoxOrderReson);
        fileImage.setImageResource(R.mipmap.excel);
        fileName.setText("文件名："+fileMode.getName());
        chBoxOrderReson.setChecked(fileMode.isChecked(),fileMode.isChecked());
        chBoxOrderReson.setClickable(false);
    }
}
