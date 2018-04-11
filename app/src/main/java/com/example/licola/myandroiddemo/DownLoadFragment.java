package com.example.licola.myandroiddemo;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.licola.myandroiddemo.utils.Logger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;

/**
 * Created by 李可乐 on 2016/12/9 0009.
 */

public class DownLoadFragment extends Fragment {

  private static final String ARG_SECTION_KEY = "section_key";
  @BindView(R.id.button)
  Button button;
  @BindView(R.id.textView)
  TextView textView;
  Unbinder unbinder;

  public DownLoadFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static DownLoadFragment newInstance(String key) {
    DownLoadFragment fragment = new DownLoadFragment();
    Bundle args = new Bundle();
    args.putString(ARG_SECTION_KEY, key);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_down, container, false);

    unbinder = ButterKnife.bind(this, rootView);

    return rootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  final static OkHttpClient client = new OkHttpClient.Builder()
      .eventListener(new EventListener() {
        @Override
        public void callStart(Call call) {
          super.callStart(call);
          Logger.d();
        }

        @Override
        public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
            @Nullable Protocol protocol) {
          super.connectEnd(call, inetSocketAddress, proxy, protocol);
          Logger.d();
        }

        @Override
        public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
            @Nullable Protocol protocol, IOException ioe) {
          super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
          Logger.d();

        }

        @Override
        public void callEnd(Call call) {
          super.callEnd(call);
          Logger.d();

        }

        @Override
        public void callFailed(Call call, IOException ioe) {
          super.callFailed(call, ioe);
          Logger.d();
        }
      })
      .build();


  @OnClick(R.id.button)
  public void onViewClicked() {
    workDownLoad();
  }

  @OnClick(R.id.btn_okhttp)
  public void onViewOkHttpClicked(final View view) {

    okhttp3.Request request =
        new okhttp3.Request.Builder().url("http://publicobject.com/helloworld.txt").build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
          throw new IOException("Unexpected code " + response);
        }

        view.post(new Runnable() {
          @Override
          public void run() {
            view.setEnabled(false);
          }
        });
        Logger.d(
            "Thread:" + Thread.currentThread().toString() + " body:" + response.body().string());
      }
    });
  }

  private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

  @OnClick(R.id.btn_thread)
  public void onViewThread() {
    mBooleanThreadLocal.set(true);
    Logger.d(Thread.currentThread().toString() + " value = " + mBooleanThreadLocal.get());

    new Thread("Thread#1") {
      @Override
      public void run() {
        super.run();
        mBooleanThreadLocal.set(false);
        Logger.d(Thread.currentThread().toString() + " value = " + mBooleanThreadLocal.get());
      }
    }.start();

    new Thread("Thread#2") {
      @Override
      public void run() {
        super.run();
        Logger.d(Thread.currentThread().toString() + " value = " + mBooleanThreadLocal.get());
      }
    }.start();
  }

  private void workDownLoad() {

    final DownloadManager downloadManager =
        (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    DownloadManager.Request request =
        new DownloadManager.Request(Uri.parse("http://api.youji.pro/youji.apk"));
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download.apk");
    request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
    request.allowScanningByMediaScanner();
    request.setAllowedOverMetered(false);
    request.setTitle("下载标题");
    request.setDescription("下载描述");
    request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    request.setMimeType("application/vnd.android.package-archive");
    final long id = downloadManager.enqueue(request);

    final DownloadManager.Query query = new Query();
    final Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Cursor cursor = downloadManager.query(query.setFilterById(id));
        if (cursor != null && cursor.moveToFirst()) {
          //下载的文件到本地的目录
          String address =
              cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
          //已经下载的字节数
          int bytes_downloaded =
              cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
          //总需下载的字节数
          int bytes_total =
              cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
          //Notification 标题
          String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
          //描述
          String description =
              cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
          //下载对应id
          long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
          //下载文件名称
          String filename =
              cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
          //下载文件的URL链接
          String url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));

          if (bytes_downloaded >= bytes_total) {
            timer.cancel();
          }
          Logger.d(address, "finish=" + bytes_downloaded, "total=" + bytes_total);
        }
        cursor.close();
      }
    };
    timer.schedule(timerTask, 500, 1000);
  }
}
