package com.example.licola.myandroiddemo.utils.ui;

import android.os.Looper;
import android.util.Printer;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;

/**
 * Created by LiCola on 2018/6/26.
 */
public class BlockDetectByPrinter {


  public static void checkLooper() {
    Looper.getMainLooper().setMessageLogging(new Printer() {
      private static final String START = ">>>>> Dispatching";
      private static final String END = "<<<<< Finished";

      @Override
      public void println(String x) {
        if (x.startsWith(START)) {
          LogMonitor.getInstance().startMonitor();
        }
        if (x.startsWith(END)) {
          LogMonitor.getInstance().removeMonitor();
        }
      }
    });
  }

  public static void checkChreographer() {
    Choreographer.getInstance().postFrameCallback(new FrameCallback() {
      @Override
      public void doFrame(long frameTimeNanos) {
        LogMonitor.getInstance().removeMonitor();

        LogMonitor.getInstance().startMonitor();
        Choreographer.getInstance().postFrameCallback(this);
      }
    });
  }

}
