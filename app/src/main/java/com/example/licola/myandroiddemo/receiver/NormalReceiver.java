package com.example.licola.myandroiddemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2019-05-22
 */
public class NormalReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    LLogger.d(context,intent);
    try {
      Thread.sleep(1000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
