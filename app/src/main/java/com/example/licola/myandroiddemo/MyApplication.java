package com.example.licola.myandroiddemo;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.example.licola.myandroiddemo.utils.Logger;
import com.example.licola.myandroiddemo.utils.RomChecker;
import com.licola.llogger.LLogger;
import com.socks.library.KLog;

/**
 * Created by LiCola on 2017/6/16.
 */

public class MyApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    initCloudChannel(this);
    MultiDex.install(this);

    KLog.init(true,"Klog2");
    LLogger.init(true,"Demo",getCacheDir(),"demo-log_");
  }

  private void initCloudChannel(Context applicationContext) {
//    PushServiceFactory.init(applicationContext);
//    CloudPushService pushService = PushServiceFactory.getCloudPushService();
//    pushService.register(applicationContext, new CommonCallback() {
//      @Override
//      public void onSuccess(String response) {
//        Logger.d( "init cloudchannel success");
//      }
//      @Override
//      public void onFailed(String errorCode, String errorMessage) {
//        Logger.d( "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//      }
//    });

    Logger.d("rom:"+ RomChecker.getName());
  }
}
