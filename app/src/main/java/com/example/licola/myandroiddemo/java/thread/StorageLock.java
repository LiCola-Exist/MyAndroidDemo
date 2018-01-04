package com.example.licola.myandroiddemo.java.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 李可乐 on 2017/5/4.
 */

public class StorageLock implements Storage {

  // 仓库最大存储量
  private final int MAX_SIZE = 100;

  // 仓库存储的载体
  private LinkedList<Object> list = new LinkedList<Object>();

  //锁
  private final Lock lock = new ReentrantLock();

  //仓库满条件变量
  private final Condition full = lock.newCondition();

  //仓库空条件变量
  private final Condition empty = lock.newCondition();


  /**
   * 生产
   */
  @Override
  public void produce(int num) {
    //获得锁
    lock.lock();

    // 如果仓库剩余容量不足
    while (list.size() + num > MAX_SIZE)
    {
      System.out.println("【要生产的产品数量】:" + num + " \t 【库存量】:"
          + list.size() + "\t目标产量大于最大值 等待消耗 暂时不能执行生产任务!");
      try
      {
        // 由于条件不满足，生产阻塞
        full.await();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    // 生产条件满足情况下，生产num个产品
    for (int i = 1; i <= num; ++i)
    {
      list.add(new Object());
    }

    System.out.println("【已经生产产品数】:" + num + "\t 【现仓储量为】:" + list.size());

    //唤醒其他线程
    full.signalAll();
    empty.signalAll();

    //释放锁
    lock.unlock();
  }

  /**
   * 消费
   */
  @Override
  public void consume(int num) {
    // 获得锁
    lock.lock();

    // 如果仓库存储量不足
    while (list.size() < num)
    {
      System.out.println("【要消费的产品数量】:" + num + " \t【库存量】:"
          + list.size() + " \t消费目标数大于库存  暂时不能执行消费任务!");
      try
      {
        // 由于条件不满足，消费阻塞
        empty.await();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    // 消费条件满足情况下，消费num个产品
    for (int i = 1; i <= num; ++i)
    {
      list.remove();
    }
    System.out.println("【已经消费产品数】:" + num + " \t 【现仓储量为】:" + list.size());

    // 唤醒其他所有线程
    full.signalAll();
    empty.signalAll();

    // 释放锁
    lock.unlock();
  }
}
