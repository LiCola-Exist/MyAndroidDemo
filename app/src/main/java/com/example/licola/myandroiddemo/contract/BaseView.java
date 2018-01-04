package com.example.licola.myandroiddemo.contract;

/**
 * Created by 李可乐 on 2017/1/19 0019.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void openDialog(String date);
}
