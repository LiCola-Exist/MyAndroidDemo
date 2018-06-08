package com.example.licola.myandroiddemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2017/11/14.
 */

public class MessengerService extends Service {

  private static class MessengerHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 100:
          LLogger.d("receive msg from client:"+msg.getData().getString("msg"));
          break;
        default:
          super.handleMessage(msg);
      }
    }
  }

  private final Messenger mMessenger =new Messenger(new MessengerHandler());

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
  }
}
