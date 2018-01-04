package com.example.licola.myandroiddemo.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 李可乐 on 2017/1/24 0024.
 */

@Module
public class ActivitySuperUserModel {

    @Provides
    SuperUserModel providesSuperUserModel(){
        return new SuperUserModel("super Dagger2");
    }
}
