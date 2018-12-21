package com.example.week2_1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.week2_1.Main2Activity;
import com.example.week2_1.Main3Activity;
import com.example.week2_1.R;
import com.example.week2_1.adapter.MyAdapter;
import com.example.week2_1.bean.MyData;
import com.example.week2_1.presenter.PresenterDataImpls;
import com.example.week2_1.view.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDataFragment extends Fragment implements IView {

    private View v;
    private ListView listView;
    private String mUrl = "http://www.xieast.com/api/news/news.php";
    private PresenterDataImpls presenterDataImpls ;
    private MyAdapter adapter;
    private List<MyData.DataBean> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_data, container, false);
        listView = v.findViewById(R.id.listview);
        presenterDataImpls = new PresenterDataImpls(this);
        adapter = new MyAdapter(data,getActivity());
        listView.setAdapter(adapter);
        presenterDataImpls.startRequest(mUrl);
        setListener();
        return v;
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                intent.putExtra("url",data.get(position).getUrl().toString().trim());
                intent.putExtra("pic",data.get(position).getThumbnail_pic_s().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public void success(Object MyData) {
        MyData myData = (MyData) MyData;
        data.addAll(myData.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error(Object error) {

    }
}
