package com.example.licola.myandroiddemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 李可乐 on 2016/8/1 0001.
 * 有关像素，屏幕 的工具类
 */
public class PixelUtils {

  /**
   * 根据手机的分辨率从dp转px
   */
  public static int dp2px(@NonNull Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return Math.round(dpValue * scale + 0.5f);
  }

  public static int px2dp(@NonNull Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return Math.round(pxValue / scale + 0.5f);
  }

  public static int getDimen(@NonNull Context context, int resId) {
    return (int) context.getResources().getDimension(resId);
  }

  public static int getScreenWidth(@NonNull Context context) {
    return getPoint(context).x;
  }

  public static int getScreenHeight(@NonNull Context context) {
    return getPoint(context).y;
  }

  @NonNull public static Point getPoint(@NonNull Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    return point;
  }
}
