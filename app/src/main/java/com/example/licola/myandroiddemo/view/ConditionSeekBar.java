package com.example.licola.myandroiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by LiCola on 2017/9/18.
 * 带有各种滑动状态的SeekBar
 */

public class ConditionSeekBar extends android.support.v7.widget.AppCompatSeekBar {

  private static final String TAG = "StepSeekBar";

  OnProcessChangeListener onProcessChangeListener;

  private boolean isPressedSlide = false;//按压后滑动


  public ConditionSeekBar(Context context) {
    super(context);
  }

  public ConditionSeekBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ConditionSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setOnProcessChangeListener(
      final OnProcessChangeListener onProcessChangeListener) {
    this.onProcessChangeListener = onProcessChangeListener;

    setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onProcessChangeListener.onProgressChanged(progress, isPressedSlide);
      }

      /**
       * 数值开始变化
       * @param seekBar
       */
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        onProcessChangeListener.onProgressChangeStatus(true);
      }

      /**
       * 数值停止变化
       * @param seekBar
       */
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        onProcessChangeListener.onProgressChangeStatus(false);
      }
    });
  }

  /**
   * 只有按下并开始滑动 才会回调方法
   * 如果只按下不滑动 不会回调
   *
   * @param pressed true：开始按压滑动 false：结束按压滑动
   */
  @Override
  public void setPressed(boolean pressed) {
    super.setPressed(pressed);
    this.isPressedSlide = pressed;
  }

  public interface OnProcessChangeListener {

    /**
     * @param progress 进度值
     * @param isPressedSlide 是否来自按压滑动
     */
    void onProgressChanged(int progress, boolean isPressedSlide);

    /**
     * 进度值得改变状态
     *
     * @param isStart true：表示开始改变 false：表示停止改变
     */
    void onProgressChangeStatus(boolean isStart);
  }
}
