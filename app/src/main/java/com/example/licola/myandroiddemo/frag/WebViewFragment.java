package com.example.licola.myandroiddemo.frag;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.aty.WebViewAty;
import com.licola.llogger.LLogger;


/**
 * A simple {@link Fragment} subclass. Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public WebViewFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment WebViewFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static WebViewFragment newInstance(String param1) {
    WebViewFragment fragment = new WebViewFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View viewRoot = inflater.inflate(R.layout.fragment_web_view, container, false);

    WebView webView = viewRoot.findViewById(R.id.web_view);
    View fabAction = viewRoot.findViewById(R.id.fab_action);
    fabAction.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getContext(), WebViewAty.class));
      }
    });
    initSettings(webView);
    initClient(webView);
    webView.loadUrl("https://github.com/LiCola");

    return viewRoot;
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
        super.onPageStarted(view, url, favicon);
        LLogger.d();
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        LLogger.d();
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

      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(getContext())
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
    };

    webView.setWebChromeClient(chromeClient);

    webView.setWebViewClient(client);
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
