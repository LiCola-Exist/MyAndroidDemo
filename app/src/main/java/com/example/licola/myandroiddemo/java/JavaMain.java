package com.example.licola.myandroiddemo.java;

import com.example.licola.myandroiddemo.utils.Logger;
import com.google.common.collect.Maps;
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
  }

  private static void testSingle() {
    Logger.d("SingleInit.name:" + SingleInit.name);
    Logger.d("SingleInit instance:" + SingleInit.getInstance());
    Class<SingleInit> initClass = null;
  }

  private static int cale() {
    int a = 100;
    int b = 200;
    int c = 300;
    return (a + b) * c;
  }


}
