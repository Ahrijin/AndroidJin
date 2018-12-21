package com.example.week2_1.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.week2_1.MainActivity;
import com.example.week2_1.R;
import com.example.week2_1.view.IView;
import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCodeFragment extends Fragment {

    private View v;
    private ImageView images;
    private Button tuiLogin;
    private String content = "时间是让人猝不及防的东西，岁月是一场有去无回的旅行";
    private String name;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_code, container, false);
        images = v.findViewById(R.id.iamgs);
        tuiLogin = v.findViewById(R.id.tuiLogin);
        //接收值
        Bundle arguments = getArguments();
        name = arguments.getString("name");
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        try {
            Bitmap bitmap = CodeCreator.createQRCode(name, 300, 300, logo);
            images.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //退出登录    获取SharedPreferences的值
        sp = getActivity().getSharedPreferences("jyy", Context.MODE_PRIVATE);
        editor = sp.edit();
        setListener();
        return v;
    }

    private void setListener() {
        tuiLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取记住密码和自动登录的状态
                editor.putBoolean("jizhumima",false);
                editor.putBoolean("zidong",false);
                editor.commit();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();//页面关闭
            }
        });
    }

}
