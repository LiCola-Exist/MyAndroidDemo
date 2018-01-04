package com.example.licola.myandroiddemo.java;

import com.example.licola.myandroiddemo.utils.Logger;

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
    Logger.d("single init method this instance");
  }

  static {
//    name="123s";
    Logger.d("single init block this class");
  }
}
