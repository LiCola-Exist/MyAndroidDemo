package com.example.licola.myandroiddemo.componet;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/**
 * @author LiCola
 * @date 2019-06-28
 */
public class RetrofitHelper {

  public static Executor defaultCallbackExecutor(){
    return new MainThreadExecutor();
  }

  static class MainThreadExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override public void execute(Runnable r) {
      handler.post(r);
    }
  }
}
