package com.example.licola.myandroiddemo.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.example.licola.myandroiddemo.utils.Logger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LiCola on 2018/5/7.
 */
public class ThreadWork {

  public static final void main(){
//    unsafeConcurrentUpdate(new HashMap<Integer, Integer>());
//    unsafeConcurrentUpdate(new ConcurrentHashMap<Integer, Integer>());
//    unsafeConcurrentUpdate(Collections.synchronizedMap(new HashMap<Integer, Integer>()));

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


}
