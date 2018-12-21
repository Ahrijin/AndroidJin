package com.example.week2_1.view;

public interface IView<T> {
    void success(T MyData);
    void error(T error);
}
