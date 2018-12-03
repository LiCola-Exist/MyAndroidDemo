package com.example.licola.myandroiddemo.http;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.licola.llogger.LLogger;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * @author LiCola
 * @date 2018/12/3
 */
public class HttpDownLoaderService extends IntentService {

  public static final String ACTION = "http.HttpDownLoaderService";

  private static final String TAG = "HttpDownLoaderService";

  private static final String ACTION_DOWN_URL = "down_url";
  public static final String ACTION_LOAD_PATH = "load_path";


  private final OkHttpClient client;

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public HttpDownLoaderService(String name) {
    super(name);
    client = new OkHttpClient.Builder()
        .build();
  }

  public HttpDownLoaderService() {
    this(TAG);
  }


  public static void startAction(Context context, String url, String filePath) {
    Intent intent = new Intent(context, HttpDownLoaderService.class);
    intent.putExtra(ACTION_DOWN_URL, url);
    intent.putExtra(ACTION_LOAD_PATH, filePath);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    if (intent == null) {
      return;
    }

    Bundle extras = intent.getExtras();
    if (extras == null) {
      return;
    }

    String actionUrl = extras.getString(ACTION_DOWN_URL);
    String actionPath = extras.getString(ACTION_LOAD_PATH);
    LLogger.d(actionUrl);

    File targetFile = new File(actionPath);
    targetFile.deleteOnExit();

    try {
      downLoadTask(actionUrl, targetFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Intent actionIntent = new Intent(ACTION);
    actionIntent.putExtra(ACTION_LOAD_PATH, actionPath);
    LocalBroadcastManager.getInstance(this).sendBroadcast(actionIntent);
  }

  private void downLoadTask(String actionUrl, File targetFile) throws IOException {
    final Request request = new Builder()
        .url(actionUrl)
        .build();
    Call call = client.newCall(request);
    Response response = call.execute();
    ResponseBody body = response.body();
    BufferedSource bufferedSource = body.source();

    try (BufferedSink sink = Okio.buffer(Okio.sink(targetFile))) {
      sink.writeAll(bufferedSource);
    }

  }

  @Override
  public void onCreate() {
    super.onCreate();
    LLogger.d();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LLogger.d();
  }
}
