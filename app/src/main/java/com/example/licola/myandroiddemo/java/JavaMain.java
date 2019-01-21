package com.example.licola.myandroiddemo.java;

import com.google.common.collect.Maps;
import com.licola.llogger.LLogger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by LiCola on 2017/9/5.
 */

public class JavaMain {

  private static final String TAG = "JavaMain";

  public static final void main() {

    cale();

    testSingle();

    HashMap<Object, Object> hashMap = Maps.newHashMapWithExpectedSize(10);

    LocalDate date=LocalDate.now();
    Instant instant=Instant.now();

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
