package com.example.week2_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.week2_1.fragment.MyCodeFragment;
import com.example.week2_1.fragment.MyDataFragment;

public class Main2Activity extends AppCompatActivity {

    private FrameLayout frame_layout;
    private RadioGroup rg;
    private MyDataFragment myDataFragment;
    private MyCodeFragment myCodeFragment;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myCodeFragment = new MyCodeFragment();
        initView();
        setListener();
        manager.beginTransaction().replace(R.id.frame_layout,new MyDataFragment()).commit();
        //将第一个页面传来的值存到bundle中
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        //我的名片接受传过来的name值
        myCodeFragment.setArguments(bundle);

    }

    private void setListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.my_data:
                        manager.beginTransaction().replace(R.id.frame_layout,new MyDataFragment()).commit();
                        break;
                    case R.id.my_code:
                        manager.beginTransaction().replace(R.id.frame_layout,myCodeFragment).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
        rg = (RadioGroup) findViewById(R.id.rg);
        manager = getSupportFragmentManager();
    }
}
