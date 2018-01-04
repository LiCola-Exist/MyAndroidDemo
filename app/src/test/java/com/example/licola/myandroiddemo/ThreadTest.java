package com.example.licola.myandroiddemo;

import com.example.licola.myandroiddemo.java.thread.Consumer;
import com.example.licola.myandroiddemo.java.thread.Producer;
import com.example.licola.myandroiddemo.java.thread.Storage;
import com.example.licola.myandroiddemo.java.thread.StorageLock;
import com.example.licola.myandroiddemo.java.thread.StorageWaitNotify;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/4.
 */

public class ThreadTest {

  @Test
  public void waitAndNotify(){
    // 仓库对象
//    Storage storage = new StorageWaitNotify();
    Storage storage = new StorageLock();

    // 生产者对象
    Producer p1 = new Producer(storage);
    Producer p2 = new Producer(storage);
    Producer p3 = new Producer(storage);

    // 消费者对象
    Consumer c1 = new Consumer(storage);
    Consumer c2 = new Consumer(storage);

    // 设置生产者产品生产数量
    p1.setNum(20);
    p2.setNum(30);
    p3.setNum(50);

    // 设置消费者产品消费数量
    c1.setNum(60);
    c2.setNum(40);

    p1.start();
    c2.start();
    // 线程开始执行
    c1.start();

    p2.start();
    p3.start();
  }

  @Test
  public void ThreadPool(){
    ExecutorService service= Executors.newCachedThreadPool();
    service.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("Thread= "+Thread.currentThread().toString());
      }
    });

    new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("This Thread= "+Thread.currentThread().toString());
      }
    }).start();
  }

}
