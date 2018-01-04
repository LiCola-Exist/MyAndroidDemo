package com.example.licola.myandroiddemo.dagger;

import dagger.Component;

/**
 * Created by 李可乐 on 2017/1/24 0024.
 */

@Component(modules = ActivitySuperUserModel.class)
public interface ActivitySuperComponent {
//    /**
//     * interface的inject方法需要一个消耗依赖的类型对象作为参数。
//     * @param activity
//     */
//    void inject(MainActivity activity);


    SuperUserModel superUserModel();
}
