package com.example.licola.myandroiddemo.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 李可乐 on 2017/1/24 0024.
 * 负责提供依赖的组件 称为Module
 */
@Module
public class ActivityUserModel {


    /**
     *  Provides标识提供依赖的方法
     * @return
     */
    @Provides
    UserModel provideUserModel() {
        //将创建代码独立出来
        return new UserModel("Dagger2");
    }

}
