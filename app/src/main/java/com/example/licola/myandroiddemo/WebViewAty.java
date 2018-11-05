package com.example.licola.myandroiddemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2018/11/5
 */
public class WebViewAty extends BaseActivity {

  private WebView mWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web);

    LinearLayout layoutGroup = findViewById(R.id.layout_group);
    WebView webView = findViewById(R.id.web_view);
    mWebView = webView;
    initSettings(webView);
    initClient(webView);
//    webView.loadUrl("https://github.com/LiCola");
    webView.loadUrl("http://www.baidu.com");
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
