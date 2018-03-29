package com.example.licola.myandroiddemo.java;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.example.licola.myandroiddemo.utils.Logger;
import java.io.Serializable;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LiCola on 2017/9/5.
 */

public class MainWork{

  private static final String TAG = "MainWork";

  public static final void work() {
    //testCopyArrayList();
    //testList();
    //unsafeConcurrentUpdate(new HashMap<Integer, Integer>());
    //unsafeConcurrentUpdate(new ConcurrentHashMap<Integer, Integer>());
    //unsafeConcurrentUpdate(Collections.synchronizedMap(new HashMap<Integer, Integer>()));

//    threadWorkHandler();
//    workHandlerDelayed();
    handlerThread();
    cale();


   }



  private static int cale() {
    int a = 100;
    int b = 200;
    int c = 300;
    return (a + b) * c;
  }

  private static void handlerThread() {
//    HandlerThread handlerThread=new HandlerThread("work");
//    handlerThread.run();
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
        Logger.d("post delayed work 1");
      }
    }, 5);
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Logger.d("post delayed work 2");
      }
    }, 5);
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Logger.d("post delayed work 3");
      }
    }, 5);
//
//    handler.post(new Runnable() {
//      @Override
//      public void run() {
//        Logger.d("post work");
//      }
//    });

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

  /**
   * 会发生 死循环的 多线程写HashMap
   */
  private static void unsafeConcurrentUpdate(final Map<Integer, Integer> map) {
    final int size = 10000;
    final Random rnd = new Random();
    for (int i = 0; i < size; i++) {
      Thread t = new Thread() {
        @Override
        public void run() {
          for (int j = 0; j < size; j++) {
            map.put(rnd.nextInt(size), 1);
          }
        }
      };
      t.start();
    }
  }

  private static void testList() {

    HashMap<Integer, String> hashMap = new HashMap<>();
    hashMap.put(1, "1");
    hashMap.put(2, "2");
    hashMap.put(3, "3");

    Set<Entry<Integer, String>> entries2 = hashMap.entrySet();
    for (Entry<Integer, String> entry : entries2) {
      Logger.d(entry.getKey() + ":" + entry.getValue());
    }

    LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("asd", 2);
    linkedHashMap.get("asd");
  }

  private static void testCopyArrayList() {
    CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    list.add("c");
    list.add("b");
    list.add("a");

    //Collections.sort(list);//android 的java版本 应该是java6  但是只有在java8以上才能支持 迭代器的操作

    for (String s : list) {
      Logger.d("for item:" + s);
    }
  }
}
