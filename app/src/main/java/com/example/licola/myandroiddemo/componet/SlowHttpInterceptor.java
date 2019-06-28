package com.example.licola.myandroiddemo.componet;

import com.licola.llogger.LLogger;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author LiCola
 * @date 2019-06-28
 */
public class SlowHttpInterceptor implements Interceptor {


  private static final int SLOW_RATE = 2;

  @Override
  public Response intercept(Chain chain) throws IOException {
    long start = System.currentTimeMillis();
    Response response = chain.proceed(chain.request());
    long end = System.currentTimeMillis();
    long duration = end - start;

    long slowDuration = duration * SLOW_RATE;
    LLogger.d(String.format("开启慢速模拟，实际请求时间%dms 慢速成%dms", duration, slowDuration));
    try {
      Thread.sleep(slowDuration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return response;
  }
}
