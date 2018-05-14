package com.example.licola.myandroiddemo.thread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.example.licola.myandroiddemo.utils.Logger;

/**
 * Created by LiCola on 2018/5/7.
 */
public class MyHandler {

  private static final String TAG = "MyHandler";

  public static final void main(){
//    threadWorkHandler();
//    workHandlerDelayed();
//    handlerThread();

    //    DispatcherTime dispatcher = new DispatcherTime();
//    dispatcher.run();
//    dispatcher.postSendTask("1", "data1");
//    dispatcher.postSendTask("2", "data2");
//
//    dispatcher.postAckTask("1");

    AsyncWorker asyncWorker=new AsyncWorker();
    asyncWorker.execute("data1");
//    asyncWorker.execute("data2");
  }

  private static class AsyncWorker extends AsyncTask<String,Integer,String>{

    @Override
    protected String doInBackground(String... strings) {
      Logger.d((Object[]) strings);
      return strings[0];
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      Logger.d((Object[]) values);
      super.onProgressUpdate(values);
    }
  }


  private static void threadWorkHandler() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        Looper.prepare();
        final Handler handler = new Handler() {
          @Override
          public void handleMessage(Message msg) {
            Logger.d("thread:" + Thread.currentThread() + " msg:" + msg);
            if (msg.what == -1) {
              Logger.d("handle quit msg");
              Looper.myLooper().quit();
            }
          }
        };

        Logger.d("thread:" + Thread.currentThread() + " post empty msg");
        handler.sendEmptyMessage(0);

        handler.sendEmptyMessageDelayed(-1, 1000);
        Looper.loop();

        Logger.d("thread exit:" + Thread.currentThread());
      }
    });

    Logger.d("main :" + Thread.currentThread());
    thread.start();

  }


  private static void handlerThread() {
    HandlerThread handlerThread=new HandlerThread("main");
    handlerThread.run();
  }

  private static void workHandlerDelayed() {
    Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        Logger.d(msg.toString());
      }
    };

    Logger.d("handle start SystemClock.uptimeMillis():" + SystemClock.uptimeMillis());
//    handler.sendEmptyMessage(0);
//    handler.sendEmptyMessage(1);
//    handler.sendEmptyMessage(2);

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Logger.d("post delayed main 1");
      }
    }, 5);
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Logger.d("post delayed main 2");
      }
    }, 5);
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Logger.d("post delayed main 3");
      }
    }, 5);
//
//    handler.post(new Runnable() {
//      @Override
//      public void run() {
//        Logger.d("post main");
//      }
//    });
  }


}
