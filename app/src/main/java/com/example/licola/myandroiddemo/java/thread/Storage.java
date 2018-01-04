package com.example.licola.myandroiddemo.java.thread;

/**
 * Created by 李可乐 on 2017/5/4.
 */

public interface Storage {
  void produce(int num);
  void consume(int num);
}
