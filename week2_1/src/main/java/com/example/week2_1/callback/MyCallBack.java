package com.example.week2_1.callback;

public interface MyCallBack<T> {
    void getSuccess(T MyData);
    void getError(T errors);
}
