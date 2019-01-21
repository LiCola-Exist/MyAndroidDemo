package com.example.licola.myandroiddemo.componet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.licola.llogger.LLogger;
import java.util.Collections;
import java.util.List;

/**
 * @author LiCola
 * @date 2018/12/29
 */
public class IntentHelper {

  /**
   * 打印安装应用
   */
  public static void appInfo(Context context){
    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    PackageManager mPackageManager = context.getPackageManager();
    List<ResolveInfo> mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
    //按报名排序
    Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPackageManager));

    for(ResolveInfo res : mAllApps){
      //该应用的包名和主Activity
      String pkg = res.activityInfo.packageName;
      String cls = res.activityInfo.name;
      LLogger.d("pkg_cls","pkg---" +pkg +"  cls---" + cls );
    }
  }
}
