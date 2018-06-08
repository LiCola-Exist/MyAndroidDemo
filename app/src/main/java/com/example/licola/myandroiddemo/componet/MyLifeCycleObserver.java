package com.example.licola.myandroiddemo.componet;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2018/4/10.
 */
public class MyLifeCycleObserver implements LifecycleObserver {

  private Lifecycle lifecycle;

  public MyLifeCycleObserver(Lifecycle lifecycle) {
    this.lifecycle = lifecycle;
  }

  @OnLifecycleEvent(Event.ON_CREATE)
  public void onCreate() {
    LLogger.d();
  }

  @OnLifecycleEvent(Event.ON_RESUME)
  public void onResume() {
    LLogger.d();

  }

  @OnLifecycleEvent(Event.ON_PAUSE)
  public void onPause() {
    LLogger.d();
  }


  public static void addObserver(LifecycleOwner lifecycleOwner) {

    Lifecycle lifecycle = lifecycleOwner.getLifecycle();
    lifecycle.addObserver(new MyLifeCycleObserver(lifecycle));
  }
}
