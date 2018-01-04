package com.example.licola.myandroiddemo.AndroidRuntimeCode;

import com.example.Wrapper;
import com.example.licola.myandroiddemo.utils.Logger;
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
       Logger.d("原代码输出 data="+date);
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
    Logger.d("代码调用");
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

      Logger.d(tag);
      synchronized (callback) {
        Logger.d(tag);
        try {
          Thread.sleep(time);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Logger.d(tag);
        callback.onBack(time);
      }

    }
  }

}
