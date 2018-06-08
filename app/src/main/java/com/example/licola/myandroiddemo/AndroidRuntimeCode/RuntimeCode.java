package com.example.licola.myandroiddemo.AndroidRuntimeCode;

import com.example.Wrapper;
import com.licola.llogger.LLogger;
import com.zaofeng.aspectj.DebugTrace;

/**
 * Created by 李可乐 on 2017/4/21.
 */

public class RuntimeCode {

  public void init() {

    testAsepctJ();

    testWrapperAsepctJ(new Wrapper.Component() {
      @Override
      public void method(String date) {
       LLogger.d("原代码输出 data="+date);
      }
    });


    TestInterface testInterface = new TestInterface() {

      @Override
      public void interfaceMethod() {

      }
    };

    testInterface.interfaceMethod();

  }

  public interface TestInterface {

    void interfaceMethod();
  }


  public void testWrapperAsepctJ(Wrapper.Component component){
    LLogger.d("代码调用");
    component.method("代码调用的输入数据");
  }

//  @DebugTrace
  public void testAsepctJ() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  public static class MyThread implements Runnable {

    String tag;
    long time;
    final Callback callback;

    public MyThread(String tag, long time, Callback callback) {
      this.tag = tag;
      this.time = time;
      this.callback = callback;
    }

    public interface Callback {

      void onBack(long time);
    }

    @Override
    public void run() {

      LLogger.d(tag);
      synchronized (callback) {
        LLogger.d(tag);
        try {
          Thread.sleep(time);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        LLogger.d(tag);
        callback.onBack(time);
      }

    }
  }

}
