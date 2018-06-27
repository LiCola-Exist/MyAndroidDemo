package com.example.licola.myandroiddemo.android;

import android.content.Context;
import android.text.format.DateUtils;
import com.example.licola.myandroiddemo.utils.ui.BlockDetectByPrinter;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2018/6/26.
 */
public class AndroidMain {


  public static void main(Context context){

    testDateUtils(context);

    BlockDetectByPrinter.checkLooper();

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
