package com.example.licola.myandroiddemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.licola.myandroiddemo.utils.Logger;

/**
 * Created by LiCola on 2018/3/28.
 */

public class MainLocalBroadcastReceiver extends BroadcastReceiver {


  @Override
  public void onReceive(Context context, Intent intent) {
    Logger.d(Thread.currentThread().toString()+": "+intent.getAction());
  }
}
