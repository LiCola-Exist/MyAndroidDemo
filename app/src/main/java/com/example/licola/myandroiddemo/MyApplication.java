package com.example.licola.myandroiddemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.demo.licola.myandroiddemo.EventBusIndex;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.example.licola.myandroiddemo.utils.RomChecker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.licola.llogger.LLogger;
import com.licola.modue.base.ServiceProvider;
import com.squareup.leakcanary.LeakCanary;
import java.io.File;
import org.greenrobot.eventbus.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by LiCola on 2017/6/16.
 */

public class MyApplication extends Application {

  private static final boolean CHECK_LEAK = true;

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }


  @Override
  public void onCreate() {
    super.onCreate();

    //初始化推送
    initCloudChannel(this);
    //多dex装载

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/base.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build());

    //fresco的初始化
    Fresco.initialize(this);

    EventBus.builder()
        .addIndex(new EventBusIndex())
        .eventInheritance(false)
        .throwSubscriberException(true)
        .installDefaultEventBus();

    //log日志初始化
    File logFileDir = new File(getCacheDir(), "log");
    LLogger.init(true, "Demo", logFileDir);

    /**
     * 初始化Stetho 数据库 调试工具 发布版注释
     */
    Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        .build());

    if (CHECK_LEAK) {
      //内存检测工具
      if (!LeakCanary.isInAnalyzerProcess(this)) {
        LeakCanary.install(this);
      }
    }

    BlockCanary.install(this, new AppBlockCanaryContext()).start();
    DoraemonKit.install(this);

    ServiceProvider.register(this);

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

  private class AppBlockCanaryContext extends BlockCanaryContext {

    @Override
    public String provideQualifier() {
      return "block";
    }

    @Override
    public String provideUid() {
      return "uid";
    }
  }
}
