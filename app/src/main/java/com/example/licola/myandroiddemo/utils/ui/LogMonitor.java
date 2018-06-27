package com.example.licola.myandroiddemo.utils.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2018/6/26.
 */
public class LogMonitor {

  private static LogMonitor sInstance = new LogMonitor();
  private HandlerThread mLogThread = new HandlerThread("log");
  private Handler mIoHandler;
  private static final long TIME_BLOCK = 1000L;

  private LogMonitor() {
    mLogThread.start();
    mIoHandler = new Handler(mLogThread.getLooper());
  }

  private static Runnable mLogRunnable = new Runnable() {
    @Override
    public void run() {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
      for (StackTraceElement s : stackTrace) {
        sb.append(s.toString() + "\n");
      }
      LLogger.d(sb.toString());
    }
  };

  public static LogMonitor getInstance() {
    return sInstance;
  }


  public void startMonitor() {
    mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
  }

  public void removeMonitor() {
    mIoHandler.removeCallbacks(mLogRunnable);
  }
}
