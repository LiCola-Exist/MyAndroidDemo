package com.example.licola.myandroiddemo.java;

import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2017/12/26.
 */

public class SingleInit {

  public static final String name="123s";

  private static final SingleInit ourInstance = new SingleInit();

  public static SingleInit getInstance() {
    return ourInstance;
  }

  private SingleInit() {
    LLogger.d("single init method this instance");
  }

  static {
//    name="123s";
    LLogger.d("single init block this class");
  }


}
