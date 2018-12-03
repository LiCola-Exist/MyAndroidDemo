package com.example.licola.myandroiddemo.dagger;

import com.example.licola.myandroiddemo.MainActivity;

import dagger.Component;

/**
 * Created by 李可乐 on 2017/1/24 0024.
 * component后增加ActivitySuperComponent  dependencies参数，使得一个Component成为了另一个Component的依赖。
 */
@Component(dependencies = ActivitySuperComponent.class,modules = ActivityUserModel.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}
