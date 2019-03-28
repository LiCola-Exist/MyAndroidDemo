package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2019/3/15
 */
public class TouchView extends View {

  private  VelocityTracker velocityTracker;
  private GestureDetector gestureDetector;
  private Scroller scroller;

  public TouchView(Context context) {
    super(context);
    init();
  }

  public TouchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TouchView(Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    //一般在处理滑动时 当两次滑动距离小于这个值 可以认为不是滑动
    LLogger.d("系统能识别的最小滑动距离:" + scaledTouchSlop);

     velocityTracker = VelocityTracker.obtain();
    scroller=new Scroller(getContext());
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {

    super.onTouchEvent(event);

    int actionMasked = event.getActionMasked();
    switch (actionMasked) {
      case MotionEvent.ACTION_DOWN:
        break;
      case MotionEvent.ACTION_MOVE:
        break;
      case MotionEvent.ACTION_UP:
        velocityTracker.computeCurrentVelocity(1000);
        LLogger.d(velocityTracker.getXVelocity(), velocityTracker.getYVelocity());
        break;
    }
    velocityTracker.addMovement(event);

    return true;
  }
}
