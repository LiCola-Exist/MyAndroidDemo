package com.example.licola.myandroiddemo.http;

import android.content.Context;
import android.support.annotation.Nullable;
import com.licola.llogger.LLogger;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * @author LiCola
 * @date 2019-05-21
 */
public class OkHttpHelper {

  private static OkHttpClient OK_HTTP_CLIENT;

  private static final HttpLoggingInterceptor INTERCEPTOR_LOG = new HttpLoggingInterceptor();

  private static final EventListener EVENT_LISTENER = new EventListener() {
    @Override
    public void callStart(Call call) {
      super.callStart(call);
      LLogger.d();
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
        @Nullable Protocol protocol) {
      super.connectEnd(call, inetSocketAddress, proxy, protocol);
      LLogger.d();
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
        @Nullable Protocol protocol, IOException ioe) {
      super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
      LLogger.d();

    }

    @Override
    public void callEnd(Call call) {
      super.callEnd(call);
      LLogger.d();

    }

    @Override
    public void callFailed(Call call, IOException ioe) {
      super.callFailed(call, ioe);
      LLogger.d();
    }
  };

  public static OkHttpClient makeClient(Context context) {

    if (OK_HTTP_CLIENT == null) {

      File cacheDir = context.getCacheDir();
      File okhttpCache = new File(cacheDir, "okhttp");
      if (!okhttpCache.exists()) {
        okhttpCache.mkdirs();
      }

      INTERCEPTOR_LOG.setLevel(Level.BODY);
      OK_HTTP_CLIENT = new Builder()
          .cache(new Cache(okhttpCache, 10 * 1024 * 1024))
          .addInterceptor(INTERCEPTOR_LOG)
          .eventListener(EVENT_LISTENER)
          .build();
    }

    return OK_HTTP_CLIENT;
  }
}
