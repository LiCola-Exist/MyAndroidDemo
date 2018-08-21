package com.example.licola.myandroiddemo.android;

import android.content.Context;
import android.text.format.DateUtils;
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2018/6/26
 */
public class AndroidMain {


  public static void main(Context context) {


    testClassLoad(context);

    testDateUtils(context);

//    BlockDetectByPrinter.checkLooper();


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
