package com.licola.modue.module;

import android.app.Application;
import com.licola.llogger.LLogger;
import com.licola.modue.base.IModule;

/**
 * @author LiCola
 * @date 2019/4/8
 */
public class RuntimeModule implements IModule {

  @Override
  public void bind(Application application) {
    LLogger.d("模块开始注册");
  }
}
