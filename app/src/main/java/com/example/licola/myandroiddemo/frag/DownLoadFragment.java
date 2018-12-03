package com.example.licola.myandroiddemo.frag;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.http.HttpDownLoaderService;
import com.licola.llogger.LLogger;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 李可乐 on 2016/12/9 0009.
 */

public class DownLoadFragment extends BaseFragment {

  private static final String ARG_SECTION_KEY = "section_key";

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
    rootView.findViewById(R.id.btn_down_manager).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        downLoadByManager();
      }
    });
    rootView.findViewById(R.id.btn_down_service).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        downLoadByService();
      }
    });

    return rootView;
  }

  private void downLoadByService() {


  }

  private void downLoadByManager() {

    final DownloadManager downloadManager =
        (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    DownloadManager.Request request =
        new DownloadManager.Request(Uri.parse("http://api.youji.pro/youji.apk"));
    request.setDestinationInExternalFilesDir(getContext(),Environment.DIRECTORY_DOWNLOADS, "download.apk");
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
          LLogger.d(address, "finish=" + bytes_downloaded, "total=" + bytes_total);
        }
        cursor.close();
      }
    };
    timer.schedule(timerTask, 500, 1000);
  }
}
