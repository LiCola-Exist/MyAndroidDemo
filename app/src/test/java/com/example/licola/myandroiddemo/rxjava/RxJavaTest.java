package com.example.licola.myandroiddemo.rxjava;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by LiCola on 2017/9/11.
 */

public class RxJavaTest {
  public static void main(String[] args) {
    Flowable.just("Hello world").subscribe(new Consumer<String>() {
      @Override public void accept(String s) throws Exception {
        
      }
    });
  }
}
