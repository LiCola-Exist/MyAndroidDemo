package com.licola.modue.base;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * @author LiCola
 * @date 2019/4/9
 */
public class AppUtils {


  public static Bundle getMetaData(Application context) {

    PackageItemInfo itemInfo = null;

    try {
      PackageManager packageManager = context.getPackageManager();
      String packageName = context.getPackageName();
      itemInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (itemInfo != null) {
      return itemInfo.metaData;
    }

    return null;
  }


  public static Bundle getMetaData(Activity context) {

    PackageItemInfo itemInfo = null;

    try {
      PackageManager packageManager = context.getPackageManager();
      itemInfo = packageManager.getActivityInfo(context.getComponentName(),
          PackageManager.GET_META_DATA);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (itemInfo != null) {
      return itemInfo.metaData;
    }

    return null;
  }

  public static Bundle getMetaData(Service context) {

    PackageItemInfo itemInfo = null;

    try {
      PackageManager packageManager = context.getPackageManager();
      itemInfo = packageManager.getActivityInfo(new ComponentName(context, context.getClass()),
          PackageManager.GET_META_DATA);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (itemInfo != null) {
      return itemInfo.metaData;
    }

    return null;
  }

}
