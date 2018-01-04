package com.zaofeng.aspectj;

import android.util.Log;

/**
 * Created by 李可乐 on 2017/4/13.
 */

public class DebugLog {

  public static final String TAG="DebugTrace";

  private DebugLog(){}

  public static void log(String msg){
    Log.d(TAG,msg);
  }
}
