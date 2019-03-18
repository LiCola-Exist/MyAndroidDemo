package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import com.licola.llogger.LLogger;

/**
 * @author LiCola
 * @date 2019/3/15
 */
public class FixFlingRecyclerView extends RecyclerView {

  public FixFlingRecyclerView(Context context) {
    super(context);
    init();
  }

  public FixFlingRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FixFlingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
//      try {
//        Field mMaxFlingVelocity = RecyclerView.class.getDeclaredField("mMaxFlingVelocity");
//        mMaxFlingVelocity.setAccessible(true);
//        mMaxFlingVelocity.setInt(this, (int) (getMaxFlingVelocity() / 2));
//        LLogger.d("修改滑动速度", getMaxFlingVelocity());
//      } catch (NoSuchFieldException | IllegalAccessException e) {
//        e.printStackTrace();
//      }

  }


  @Override
  public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
    return super.onNestedPreFling(target, velocityX, velocityY);
//    return true;
  }

  /**
   * 在手指抬起时触发，表示后续的滑动速度，即惯性滑动
   * 把速度变小，就会让滑动变得阻塞，直接感觉就是没有这么灵敏
   * @param velocityX velocityX是横向滑动系数，系统正是根据这个系数计算出应该滑动的距离是多少 velocityX大于0时表示向右滚动，小于0时表示向左滚动
   * @param velocityY velocityY是竖向滑动系数，系统会根据这个系统计算出竖向滑动的距离
   * @return
   */
//  @Override
//  public boolean fling(int velocityX, int velocityY) {
//
////    int flingX = (int) (velocityX * 0.4f);
//    LLogger.d(velocityX, velocityY);
//    return super.fling(velocityX, velocityY);
//  }
}
