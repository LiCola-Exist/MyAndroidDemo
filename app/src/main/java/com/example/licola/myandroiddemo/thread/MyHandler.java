package com.example.licola.myandroiddemo.thread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.os.SystemClock;
import com.licola.llogger.LLogger;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by LiCola on 2018/5/7.
 */
public class MyHandler {

  private static final String TAG = "MyHandler";

  public static final void main() {
//    threadWorkHandler();
    workHandlerDelayed();
    handlerThread();

    //    DispatcherTime dispatcher = new DispatcherTime();
//    dispatcher.run();
//    dispatcher.postSendTask("1", "data1");
//    dispatcher.postSendTask("2", "data2");
//
//    dispatcher.postAckTask("1");

    AsyncWorker asyncWorker = new AsyncWorker();
    asyncWorker.execute("data1");
//    asyncWorker.execute("data2");

    Looper.myQueue().addIdleHandler(new IdleHandler() {
      @Override
      public boolean queueIdle() {
        LLogger.d("执行空闲任务 :" + Thread.currentThread());
        return false;
      }
    });



  }

  private static class AsyncWorker extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... strings) {
      LLogger.d((Object[]) strings);
      return strings[0];
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      LLogger.d((Object[]) values);
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
            LLogger.d("thread:" + Thread.currentThread() + " msg:" + msg);
            if (msg.what == -1) {
              LLogger.d("handle quit msg");
              Looper.myLooper().quit();
            }
          }
        };

        LLogger.d("thread:" + Thread.currentThread() + " post empty msg");
        handler.sendEmptyMessage(0);

        handler.sendEmptyMessageDelayed(-1, 1000);
        Looper.loop();

        LLogger.d("thread exit:" + Thread.currentThread());
      }
    });

    LLogger.d("main :" + Thread.currentThread());
    thread.start();

  }


  private static void handlerThread() {
//    HandlerThread handlerThread = new HandlerThread("main");
//    handlerThread.run();
  }

  private static void workHandlerDelayed() {
    Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        LLogger.d(msg.toString());
      }
    };


    LLogger.d("handle start SystemClock.uptimeMillis():" + SystemClock.uptimeMillis());
//    handler.sendEmptyMessage(0);
//    handler.sendEmptyMessage(1);
//    handler.sendEmptyMessage(2);

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        LLogger.d("post delayed main 1");
      }
    };
    Message message = handler.obtainMessage();
    message.obj = runnable;

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        LLogger.d("post delayed main 2");
      }
    }, 100);
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        LLogger.d("post delayed main 3");
      }
    }, 100);

    final long start = SystemClock.uptimeMillis();//系统开启到现在的时间
    LLogger.d(start);
    handler.post(new Runnable() {
      @Override
      public void run() {
        long end = SystemClock.uptimeMillis();//系统开启到现在的时间
        LLogger.d(end - start);
      }
    });
  }


}
