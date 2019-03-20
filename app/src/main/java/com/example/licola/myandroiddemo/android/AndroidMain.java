package com.example.licola.myandroiddemo.android;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author LiCola
 * @date 2018/6/26
 */
public class AndroidMain {


  public static void main(Context context) {

    testTimeFormat();

    testClassLoad(context);

    testDateUtils(context);

    testUri(context);

    testColor(context);
    testSystemInfo(context);

  }

  private static void testTimeFormat() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    String format = sdf.format(new Date());
    LLogger.d(format);
  }

  private static void testUri(Context context) {
    File cacheDir = context.getCacheDir();
    Uri uriFile = Uri.fromFile(cacheDir);
    Uri uriHttp = Uri.parse("http://www.github.com");
    LLogger.d(uriFile,uriHttp);
  }

  private static void testSystemInfo(Context context) {

    String uuId = UUID.randomUUID().toString();
    LLogger.d("UUID:" + uuId);

    LLogger.d("Build:" + Build.MANUFACTURER);

    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    int memoryClass = manager.getMemoryClass();
    int largeMemoryClass = manager.getLargeMemoryClass();
    LLogger.d("memoryClass:" + memoryClass + " largeMemoryClass:" + largeMemoryClass);

    String packageCodePath = context.getPackageCodePath();
    String packageResourcePath = context.getPackageResourcePath();
    String codeCacheDir = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      codeCacheDir = context.getCodeCacheDir().getAbsolutePath();
    }
    LLogger.d("packageCodePath:" + packageCodePath + " packageResourcePath:" + packageResourcePath
        + " codeCacheDir:" + codeCacheDir);

    File cacheDir = context.getCacheDir();
    LLogger.d("cacheDir:" + cacheDir);

    LLogger.d(context.getResources().getDisplayMetrics().toString());

  }

  private static void testColor(Context context) {
    int parse6Color = Color.parseColor("#FFFFFF");
    int parse8Color = Color.parseColor("#FFFFFFFF");
    int color = ContextCompat.getColor(context, R.color.white_normal);
    LLogger.d(parse6Color, parse8Color, color);
    int alpha = (int) (1 * 255.0f + 0.5f);
//      FF9800
    int argb = Color.argb(alpha, 255, 152, 0);

  }

  private static void testClassLoad(Context context) {
    ClassLoader classLoader = context.getClassLoader();
    while (classLoader != null) {
      LLogger.d(classLoader.toString());
      classLoader = classLoader.getParent();
    }
  }

  private static void testDateUtils(Context context) {
    LLogger.d(DateUtils.formatDateTime(context, System.currentTimeMillis(),
        DateUtils.FORMAT_SHOW_TIME));
    LLogger.d(DateUtils.formatDateTime(context, System.currentTimeMillis(),
        DateUtils.FORMAT_ABBREV_ALL));
    LLogger.d(DateUtils.formatDateTime(context, System.currentTimeMillis(),
        DateUtils.FORMAT_ABBREV_WEEKDAY
            | DateUtils.FORMAT_ABBREV_ALL
            | DateUtils.FORMAT_ABBREV_TIME));
  }

}
