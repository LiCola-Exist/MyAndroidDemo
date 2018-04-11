package com.example.licola.myandroiddemo.java;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import com.example.licola.myandroiddemo.utils.Logger;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by LiCola on 2018/4/10.
 * 支持超时重试机制版非阻塞任务队列
 */
public class Dispatcher {

  private static final String THREAD_NAME = "dispatcher-worker";

  //超时检测时间
  private static final long CHECK_ACK_TIME_OUT = 10 * 1000;
  //任务限定等待时间，即任务超时时间
  private static final long ACK_TIME_OUT = 2 * 1000;

  private Handler mHandler;
  private HandlerThread handlerThread;

  private HashMap<String, Pair<Long, String>> tasks=new HashMap<>();//任务集合

  public void run() {
    handlerThread = new HandlerThread(THREAD_NAME);
    handlerThread.start();
    mHandler = new Handler(handlerThread.getLooper());

    //开启循环检测
    mHandler.postDelayed(checkTimeOutTask(), CHECK_ACK_TIME_OUT);
  }

  public void postSendTask(final String id, final String data) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        //发送任务的操作 如准备数据等

        Logger.d("开始发送任务");
        tasks.put(id, new Pair<>(System.currentTimeMillis(), data));
      }
    });
  }

  public void postAckTask(final String id) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        //回应任务的操作 如解析回应等

        Logger.d("开始回应任务");
        tasks.remove(id);
      }
    });
  }

  public Runnable checkTimeOutTask() {
    return new Runnable() {
      @Override
      public void run() {
        int count = 0;
        long curTime = System.currentTimeMillis();

        if (!tasks.isEmpty()) {
          for (Entry<String, Pair<Long, String>> entry : tasks.entrySet()) {
            String id = entry.getKey();
            Pair<Long, String> pair = entry.getValue();
            Long time = pair.first;
            String data = pair.second;
            if (curTime - time >= ACK_TIME_OUT) {
              postSendTask(id, data);
              count++;
            }
          }
        }

        if (count > 0) {
          Logger.d(String.format("检测到超时任务%d", count));
        }

        //循环检测
        mHandler.postDelayed(checkTimeOutTask(), CHECK_ACK_TIME_OUT);

      }
    };
  }
}
