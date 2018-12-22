package com.example.licola.myandroiddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2018/8/3
 */
public class SoftKeyboardHelper {


  static public void hideSoftKeyboard(@NonNull Context mContext, EditText editText) {
    if (editText == null) {
      return;
    }
    editText.clearFocus();
    InputMethodManager inputMethodManager =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (inputMethodManager != null) {
      inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
  }

  public static void openSoftKeyboard(@NonNull Context mContext, EditText editText) {
    if (editText == null) {
      return;
    }
    editText.requestFocus();//先获取焦点 否则无法显示软键盘
    InputMethodManager inputMethodManager =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (inputMethodManager != null) {
      inputMethodManager.showSoftInput(editText, 0);
    }
  }

  public interface OnSoftKeyboardListener {

    /**
     * @param appHeight 当前应用app的显示高度（包含状态栏）
     * @param softHeight 软键盘高度
     * @param navBarHeight 底部导航栏高度（不显示时为0）
     */
    void onChange(int appHeight, int softHeight, int navBarHeight);

  }

  /**
   * 获取软键盘得高度 依靠监听视图变化 得到软键盘高度
   */
  public static void setOnSoftKeyboardHeight(final Activity activity,
      final OnSoftKeyboardListener listener) {

    if (VERSION.SDK_INT >= VERSION_CODES.KITKAT_WATCH) {
      activity.getWindow().getDecorView()
          .setOnApplyWindowInsetsListener(new OnSoftChangeHeightListener(listener));
    } else {
      activity.getWindow().getDecorView().getViewTreeObserver()
          .addOnGlobalLayoutListener(new OnSoftChangeLowListener(activity, listener));
    }
  }

  private static class OnSoftChangeHeightListener implements OnApplyWindowInsetsListener {

    private OnSoftKeyboardListener listener;

    private Rect decorViewRect;

    public OnSoftChangeHeightListener(
        OnSoftKeyboardListener listener) {
      this.listener = listener;
      this.decorViewRect = new Rect();
    }

    @Override
    public WindowInsets onApplyWindowInsets(View decorView, WindowInsets insets) {
      int stableInsetBottom = insets.getStableInsetBottom();
      int systemWindowInsetBottom = insets.getSystemWindowInsetBottom();

      LLogger.d(stableInsetBottom, systemWindowInsetBottom);

      decorView.getWindowVisibleDisplayFrame(decorViewRect);

      int navBarHeight = stableInsetBottom;

      int softHeight = systemWindowInsetBottom - navBarHeight;
      listener.onChange(decorViewRect.bottom, softHeight, navBarHeight);

      //打开
      //      argument[0]=130
      //    	argument[1]=933
      //关闭
      //    	argument[0]=130
      //    	argument[1]=130

      //全面屏 打开
      // 	    argument[0]=0
      //    	argument[1]=803

      //全面屏 关闭
      //      argument[0]=0
      //    	argument[1]=0

      return insets;
    }
  }


  private static class OnSoftChangeLowListener implements OnGlobalLayoutListener {

    private View decorView;
    private OnSoftKeyboardListener listener;
    private int navigationBarMetricsHeight;
    private Rect viewRootRect;

    private boolean lastState;

    public OnSoftChangeLowListener(Activity activity, OnSoftKeyboardListener listener) {
      this.decorView = activity.getWindow().getDecorView();
      this.listener = listener;
      this.navigationBarMetricsHeight = WindowsController.getNavigationBarHeightMetrics(activity);
      this.viewRootRect = new Rect();
      this.lastState = false;
    }

    @Override
    public void onGlobalLayout() {
      //根view宽高 即app显示内容高度 会被软键盘挤压
      decorView.getWindowVisibleDisplayFrame(viewRootRect);

      //实际的屏幕高
      int screenHeight = decorView.getRootView().getHeight();

      int appHeight = screenHeight - navigationBarMetricsHeight;
      int diffHeight = appHeight - viewRootRect.bottom;

      //大于0表示 软键盘打开
      boolean newState = diffHeight > 0;
      if (newState == lastState) {
        //防止重复回调
        return;
      }
      this.listener.onChange(viewRootRect.bottom, diffHeight, navigationBarMetricsHeight);
      this.lastState = newState;
    }
  }
}
