package com.example.licola.myandroiddemo.java;

import android.os.Handler;
import android.os.HandlerThread;
import com.example.licola.myandroiddemo.utils.Logger;
import java.util.HashMap;

/**
 * Created by LiCola on 2018/4/10.
 * 支持超时重试机制版非阻塞任务队列
 */
public class DispatcherTime {

  private static final String THREAD_NAME = "dispatcher-worker";

  //任务限定等待时间，即任务超时时间
  private static final long ACK_TIME_OUT = 2 * 1000;

  private Handler mHandler;
  private HandlerThread handlerThread;

  private HashMap<String, Runnable> timeoutTask = new HashMap<>();//超时集合

  public void run() {
    handlerThread = new HandlerThread(THREAD_NAME);
    handlerThread.start();
    mHandler = new Handler(handlerThread.getLooper());
  }

  public void postSendTask(final String id, final String data) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        //发送任务的操作 如准备数据等

        Logger.d("开始发送任务",data);
        Runnable checkTimeOutTask = checkTimeOutTask(id, data);
        timeoutTask.put(id, checkTimeOutTask);


        mHandler.postDelayed(checkTimeOutTask,ACK_TIME_OUT);
      }
    });
  }

  public void postAckTask(final String id) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        //回应任务的操作 如解析回应等

        Logger.d("开始回应任务",id);
        Runnable runnable = timeoutTask.remove(id);
        mHandler.removeCallbacks(runnable);
      }
    });
  }

  public Runnable checkTimeOutTask(final String id, final String data) {
    return new Runnable() {
      @Override
      public void run() {

        Logger.d("超时任务执行 ",id,data);
        postSendTask(id, data);
      }
    };
  }
}
