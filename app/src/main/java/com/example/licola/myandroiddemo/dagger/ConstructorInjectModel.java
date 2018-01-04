package com.example.licola.myandroiddemo.dagger;

import javax.inject.Inject;

/**
 * Created by 李可乐 on 2017/1/24 0024.
 */

public class ConstructorInjectModel {

    private String constr;

    @Inject
    public ConstructorInjectModel(String constr) {
        this.constr = constr;
    }
}
