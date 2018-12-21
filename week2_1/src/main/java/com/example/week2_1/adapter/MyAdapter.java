package com.example.week2_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.week2_1.R;
import com.example.week2_1.bean.MyData;


import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<MyData.DataBean> mList;
    private Context context;

    public MyAdapter(List<MyData.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.list_item,null);
            holder = new MyHolder();
            holder.titles = convertView.findViewById(R.id.titles);
            holder.imgs = convertView.findViewById(R.id.imags);
            convertView.setTag(holder);
        }else {
            holder = (MyHolder)convertView.getTag();
        }
        holder.titles.setText(mList.get(position).getTitle());
        Glide.with(convertView).load(mList.get(position).getThumbnail_pic_s()).into(holder.imgs);
        return convertView;
    }

    class MyHolder{
        ImageView imgs;
        TextView titles;
    }
}
