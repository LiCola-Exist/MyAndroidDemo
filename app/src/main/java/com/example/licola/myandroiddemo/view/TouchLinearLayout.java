package com.example.licola.myandroiddemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author LiCola
 * @date 2018/12/20
 */
public class TouchLinearLayout extends LinearLayout {

  private boolean interceptTouch = true;

  public TouchLinearLayout(Context context) {
    super(context);
  }

  public TouchLinearLayout(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setInterceptTouch(boolean interceptTouch) {
    this.interceptTouch = interceptTouch;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (interceptTouch) {
      return true;
    } else {
      return super.onInterceptTouchEvent(ev);
    }
  }
}
