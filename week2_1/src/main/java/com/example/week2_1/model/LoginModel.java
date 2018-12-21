package com.example.week2_1.model;

import com.example.week2_1.callback.MyCallBack;

public interface LoginModel {
    void getLoginData(String url,MyCallBack callBack);
}
