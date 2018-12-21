package com.example.week2_1.model;

import com.example.week2_1.callback.MyCallBack;

public interface DataModel {
    void getData(String url, MyCallBack callBack);
}
