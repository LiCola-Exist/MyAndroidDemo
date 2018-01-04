package com.example.licola.myandroiddemo.contract;

/**
 * Created by 李可乐 on 2017/1/19 0019.
 */

public interface GuideContract {
    interface View extends BaseView<Presenter> {
        void showGuide(String date);
    }

    interface Presenter extends BasePresenter {
        void checkGuideShow();
    }
}
