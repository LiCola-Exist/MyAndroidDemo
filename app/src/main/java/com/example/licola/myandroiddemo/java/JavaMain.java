package com.example.licola.myandroiddemo.java;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.licola.llogger.LLogger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by LiCola on 2017/9/5.
 */

public class JavaMain {

  private static final String TAG = "JavaMain";

  public static final void main() {


    cale();

    testSingle();

    HashMap<Object, Object> hashMap = Maps.newHashMapWithExpectedSize(10);


    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      heightApi();
    }


    Class<?> xposedClass = null;
    try {
      xposedClass = Class.forName("de.robv.android.xposed.XposedBridge",false,ClassLoader.getSystemClassLoader());
    } catch (ClassNotFoundException e) {
    }
    if (xposedClass != null) {
      LLogger.d("运行在Xposed环境");
    }

    LinkedList<String> linkedList=new LinkedList<>();

    linkedList.add("1");

    linkedList.remove("1");
  }

  @RequiresApi(api = VERSION_CODES.O)
  private static void heightApi() {
    LocalDate date = LocalDate.now();
    Instant instant = Instant.now();
  }

  private static void testSingle() {
    LLogger.d("SingleInit.name:" + SingleInit.name);
    LLogger.d("SingleInit instance:" + SingleInit.getInstance());
    Class<SingleInit> initClass = null;
  }

  private static int cale() {
    int a = 100;
    int b = 200;
    int c = 300;
    return (a + b) * c;
  }


}
