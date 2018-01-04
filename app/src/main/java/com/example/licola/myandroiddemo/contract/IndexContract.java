package com.example.licola.myandroiddemo.contract;

/**
 * Created by 李可乐 on 2017/1/19 0019.
 */

public interface IndexContract {
    interface View extends BaseView<Presenter> {
        void showIndex(int type);
    }

    interface Presenter extends BasePresenter {
        void showIndex();
    }
}
