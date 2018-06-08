package com.example.licola.myandroiddemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.example.licola.myandroiddemo.utils.RomChecker;
import com.licola.llogger.LLogger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by LiCola on 2017/6/16.
 */

public class MyApplication extends Application {

  private RefWatcher refWatcher;

  @Override
  public void onCreate() {
    super.onCreate();
    initCloudChannel(this);
    MultiDex.install(this);

    LLogger.init(true, "Demo", getCacheDir(), "demo-log_");

    if (!LeakCanary.isInAnalyzerProcess(this)) {
      refWatcher = LeakCanary.install(this);
    }
  }

  public static RefWatcher getRefWatcher(Context context) {
    MyApplication applicationContext = (MyApplication) context.getApplicationContext();
    return applicationContext.refWatcher;
  }

  private void initCloudChannel(Context applicationContext) {
//    PushServiceFactory.init(applicationContext);
//    CloudPushService pushService = PushServiceFactory.getCloudPushService();
//    pushService.register(applicationContext, new CommonCallback() {
//      @Override
//      public void onSuccess(String response) {
//        LLogger.d( "init cloudchannel success");
//      }
//      @Override
//      public void onFailed(String errorCode, String errorMessage) {
//        LLogger.d( "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//      }
//    });

    LLogger.d("rom:" + RomChecker.getName());
  }
}
