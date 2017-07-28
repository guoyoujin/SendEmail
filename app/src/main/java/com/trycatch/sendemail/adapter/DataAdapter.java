package com.trycatch.sendemail.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trycatch.sendemail.R;
import com.trycatch.sendemail.vo.FileMode;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.adapter.DataAdapter.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-28 13:25
 * @version: V1.0 <描述当前版本功能>
 */

public class DataAdapter  extends ListBaseAdapter<FileMode> {
    private int type_value = 0;
    private Context context;
    public DataAdapter(Context context) {
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
        fileImage.setImageResource(R.mipmap.cartoon_folder);
        fileName.setText("文件名："+fileMode.getName());
        fileCount.setText("文件数量："+fileMode.getCount());
        if(fileMode.getType()=="0"){
            holder.getView(R.id.chBoxOrderReson).setVisibility(View.GONE);
        }
    }
}
