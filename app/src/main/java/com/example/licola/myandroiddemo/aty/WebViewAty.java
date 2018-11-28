package com.example.licola.myandroiddemo.aty;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnChildScrollUpCallback;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.StringUtils;
import com.licola.llogger.LLogger;
import java.io.File;

/**
 * @author LiCola
 * @date 2018/11/5
 */
public class WebViewAty extends BaseActivity {

  private static final String BASE_ULR = "https://fir.im/dm2p";

  private WebView mWebView;
  private SwipeRefreshLayout mSwipeRefresh;

  private DownBroadcastReceiver downBroadcastReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    checkInstallPermission(mContext);

    setContentView(R.layout.activity_web);
    mSwipeRefresh = findViewById(R.id.swipe_refresh);
    mSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        onBaseUrl();
      }
    });

    mSwipeRefresh.setOnChildScrollUpCallback(new OnChildScrollUpCallback() {
      @Override
      public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
        if (mWebView != null) {
          return mWebView.getScrollY() > 0;
        }

        return false;
      }
    });

    LinearLayout layoutGroup = findViewById(R.id.layout_group);
    WebView webView = findViewById(R.id.web_view);
    mWebView = webView;
    initSettings(webView);
    initClient(webView);
    initDownLoad(webView);
//    webView.loadUrl("https://github.com/LiCola");
    webView.loadUrl(BASE_ULR);
  }

  private static final int REQUEST_INSTALL_PERMISSION = 102;

  private void checkInstallPermission(Context mContext) {
    //不仅代码版本需要 O 还需要在targetSdkVersion中指定大于等于O的版本 否则canRequestPackageInstalls用于返回false
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      boolean installPermission = mContext.getPackageManager().canRequestPackageInstalls();
      if (installPermission) {
        LLogger.d("已经有安装未知应用权限");
      } else {
        LLogger.d("没有安装未知应用权限");
        Uri packageUri = Uri.parse("package:" + mContext.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
        startActivityForResult(intent, REQUEST_INSTALL_PERMISSION);
      }
    }

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (RESULT_OK == resultCode && REQUEST_INSTALL_PERMISSION == requestCode) {
      LLogger.d("安装授权");
    }
  }

  private void initDownLoad(WebView webView) {
    webView.setDownloadListener(new DownloadListener() {
      @Override
      public void onDownloadStart(String url, String userAgent, String contentDisposition,
          String mimetype, long contentLength) {
        LLogger.d(url, userAgent, contentDisposition, mimetype, contentLength);
        startDownLoadByManager(url);
      }
    });
  }

  private void startDownLoadByManager(String url) {
    final DownloadManager downloadManager =
        (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    String nameApk = StringUtils.md5hex(url) + ".apk";
    DownloadManager.Request request =
        new DownloadManager.Request(Uri.parse(url));
    File downDirs = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
    File fileApk = new File(downDirs, nameApk);
    String filePathApk = fileApk.getAbsolutePath();
    LLogger.d(nameApk, filePathApk);
    request
        .setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS,
            nameApk);
    request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
    request.allowScanningByMediaScanner();
    request.setAllowedOverMetered(false);
    request.setTitle(nameApk);
    request.setDescription("下载描述");
    request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
    request.setMimeType("application/vnd.android.package-archive");
    long downloadId = downloadManager.enqueue(request);
    if (downBroadcastReceiver != null) {
      mContext.unregisterReceiver(downBroadcastReceiver);
      downBroadcastReceiver = null;
    }

    downBroadcastReceiver = new DownBroadcastReceiver(downloadId, filePathApk);
    mContext.registerReceiver(downBroadcastReceiver,
        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (downBroadcastReceiver != null) {
      mContext.unregisterReceiver(downBroadcastReceiver);
      downBroadcastReceiver = null;
    }
  }

  private class DownBroadcastReceiver extends BroadcastReceiver {

    private long downloadId;
    private String filePathApk;

    public DownBroadcastReceiver(long downloadId, String filePathApk) {
      this.downloadId = downloadId;
      this.filePathApk = filePathApk;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      Query query = new Query();
      query.setFilterById(downloadId);

      final DownloadManager downloadManager =
          (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
      Cursor cursor = downloadManager.query(query);
      if (cursor != null && cursor.moveToFirst()) {
        //下载文件名称
        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
        if (DownloadManager.STATUS_SUCCESSFUL == status) {
          LLogger.d("下载成功");
          String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
          LLogger.d(localUri);
          installApk(filePathApk);
        }

      }
    }
  }


  private void installApk(String filePathApk) {
    Intent intent = new Intent();

    Uri data;

    //先设置
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    File fileApk = new File(filePathApk);
    if (VERSION.SDK_INT >= VERSION_CODES.N) {
      //再添加
//      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      data = FileProvider
          .getUriForFile(mContext, "com.example.licola.myandroiddemo.fileprovider",
              fileApk);
    } else {
      data = Uri.fromFile(fileApk);
    }
    intent.setAction(Intent.ACTION_VIEW);

    LLogger.d(filePathApk, data);
    intent.setDataAndType(data, "application/vnd.android.package-archive");
    startActivity(intent);
  }

  private void onBaseUrl() {
    mWebView.loadUrl(BASE_ULR);
  }

  private void initClient(final WebView webView) {
    WebViewClient client = new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        LLogger.d(request.getUrl().toString());
        webView.loadUrl(request.getUrl().toString());
        return true;
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return true;
      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LLogger.d(url);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        LLogger.d(url);
        mSwipeRefresh.setRefreshing(false);
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();    //表示等待证书响应
        // handler.cancel();      //表示挂起连接，为默认方式
        // handler.handleMessage(null);    //可做其他处理
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description,
          String failingUrl) {
        LLogger.d(errorCode, description, failingUrl);
      }

      @Override
      public void onReceivedHttpError(WebView view, WebResourceRequest request,
          WebResourceResponse errorResponse) {
        LLogger.d(errorResponse.getReasonPhrase());
      }
    };

    WebChromeClient chromeClient = new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        LLogger.d(newProgress);
      }

      /**
       * 支持javascript的警告框
       * @param view
       * @param url
       * @param message
       * @param result
       * @return
       */
      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(WebViewAty.this)
            .setTitle("JsAlert")
            .setMessage(message)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                result.confirm();
              }
            })
            .setCancelable(false)
            .show();
        return true;

      }

      /**
       * 支持javascript的确认框
       * @param view
       * @param url
       * @param message
       * @param result
       * @return
       */
      @Override
      public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
      }

      /**
       * 支持javascript输入框
       * @param view
       * @param url
       * @param message
       * @param defaultValue
       * @param result
       * @return
       */
      @Override
      public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
          JsPromptResult result) {

        return super.onJsPrompt(view, url, message, defaultValue, result);
      }
    };

    webView.setWebChromeClient(chromeClient);

    webView.setWebViewClient(client);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  private void initSettings(WebView webView) {
    WebSettings webSettings = webView.getSettings();
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setUseWideViewPort(true);
    //允许JS代码
    webSettings.setJavaScriptEnabled(true);
    webSettings.setDomStorageEnabled(true);

    //禁用缩放
    webSettings.setDisplayZoomControls(false);
    webSettings.setBuiltInZoomControls(false);

    //禁用文字缩放
    webSettings.setTextZoom(100);

    //10M缓存，api 18后，系统自动管理。
    webSettings.setAppCacheMaxSize(10 * 1024 * 1024);

    //允许缓存，设置缓存位置
    webSettings.setAppCacheEnabled(true);
    webSettings.setAppCachePath(webView.getContext().getDir("appcache", 0).getPath());

    //允许WebView使用File协议
    webSettings.setAllowFileAccess(true);
    //不保存密码
    webSettings.setSavePassword(false);

    //自动加载图片
    webSettings.setLoadsImagesAutomatically(true);

    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

  }

}
