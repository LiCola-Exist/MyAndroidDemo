package com.example.licola.myandroiddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.licola.llogger.LLogger;
import java.lang.reflect.Field;

/**
 * Created by xiao on 2015/8/7.
 */
public class WindowsController {

  /**
   * 设置透明状态栏和导航栏.
   */
  static public void setTranslucentWindows(@NonNull Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //透明状态栏
      Window window = activity.getWindow();
      //透明导航栏
      activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//      window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      window.addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//      View decorView = window.getDecorView();
//      int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//          | View.SYSTEM_UI_FLAG_FULLSCREEN;
//      decorView.setSystemUiVisibility(uiOptions);
    }
  }

  /**
   * 給状态栏加上一层透明色
   * 配合{@link #setTranslucentWindows(Activity)}方法使用
   * 主要方法为添加一个View并设置背景色添加到系统contentView中
   */
  static public void addStatusBarBackground(@NonNull Activity activity, @DrawableRes int resid) {
    int height;
    height = getStatusBarHeight(activity);
    if (height <= 0) {
      return;
    }
    FrameLayout layout = activity.findViewById(android.R.id.content);
    FrameLayout statusLayout = new FrameLayout(activity);
    statusLayout.setLayoutParams(
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

    statusLayout.setBackgroundResource(resid);
    layout.addView(statusLayout);
  }

  /**
   * 19API以上 读取到状态栏高度才有意义
   */
  static public int getStatusBarHeight(@NonNull Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      int resourceId =
          context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    } else {
      return 0;
    }
  }

  /**
   * 底部虚拟按键栏的高度
   */
  public static int getSoftButtonsBarHeight(@NonNull Activity mActivity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      DisplayMetrics metrics = new DisplayMetrics();
      //这个方法获取可能不是真实屏幕的高度
      mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
      int usableHeight = metrics.heightPixels;
      //获取当前屏幕的真实高度
      mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
      int realHeight = metrics.heightPixels;
      if (realHeight > usableHeight) {
        return realHeight - usableHeight;
      } else {
        return 0;
      }
    }
    return 0;
  }

  static public void hideSoftKeyboard(@NonNull Context mContext, @NonNull View view) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void openSoftKeyboard(@NonNull Context mContext, View view) {
    InputMethodManager inManager =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    inManager.showSoftInput(view, 0);
  }

  /**
   * 获取软键盘得高度
   * 依靠监听视图变化 得到软键盘高度
   */
  public static void getSoftKeyboardHeight(@NonNull final View view) {
    view.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

          @Override
          public void onGlobalLayout() {
            Rect r = new Rect();
            // r will be populated with the coordinates of your view that area still visible.
            view.getWindowVisibleDisplayFrame(r);
            int screenHeight = view.getRootView().getHeight();
            int heightDiff = screenHeight - (r.bottom - r.top);
            int statusBarHeight = 0;
            if (heightDiff > 100)
            // if more than 100 pixels, its probably a keyboard
            // get status bar height

            {
              try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = view.getContext().getResources().getDimensionPixelSize(x);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            int realKeyboardHeight = Math.abs(heightDiff - statusBarHeight);
            LLogger.d("keyboard height = " + realKeyboardHeight);
          }
        });
  }

  static public void setTransparentStatusBar(@NonNull Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }
}
