package com.example.licola.myandroiddemo.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.widget.ImageView;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2017/9/15.
 */

public class ImageProgressView {

  private final ImageView imageView;
  private final Drawable[] drawables;
  private final int step;//步长

  public ImageProgressView(ImageView imageView, Drawable[] drawables) {
    this.imageView = imageView;
    this.drawables = drawables;
    step = (100 / drawables.length);
    updateByProcessValue(0);
  }


  int lastProcess;
  int stepIndex = 0;//步长的索引

  boolean isChange;
  boolean isEffectFirst = true;

  public void updateChange(boolean isChange) {
    this.isChange = isChange;
    if (!isChange) {
      //结束时 重置
      isEffectFirst = true;
    }
  }

  /**
   * 根据 移动步长 更新索引
   */
  public void updateByProcessStep(@IntRange(from = 0, to = 100) int process, boolean isPressed) {
    LLogger.d("isPressed:" + isPressed + " lastProcess:" + lastProcess + " process:" + process);

    if (!isPressed) {
      //无效的移动状态 只更新进度值
      this.lastProcess = process;
      return;
    }

    if (isChange && isEffectFirst) {
      //开始改变 并且是首次影响值 只更新进度值 和设置非首次
      isEffectFirst = false;
      this.lastProcess = process;
      return;
    }

    int oneStep = process - lastProcess;
    if (Math.abs(oneStep) >= step) {
      //这次进度差值 大于步长 更新视图
      if (oneStep > 0) {
        //正向
        int stepDiff = oneStep / step;
        updateViewWithIndex(stepIndex + stepDiff);
      } else {
        //逆向
        int stepDiff = -oneStep / step;
        updateViewWithIndex(stepIndex - stepDiff);
      }
      this.lastProcess = process;
    }

  }

  private void updateViewWithIndex(int index) {
    int resultIndex;
    if (index < 0) {
      resultIndex = 0;
    } else if (index > drawables.length - 1) {
      resultIndex = drawables.length - 1;
    } else {
      resultIndex = index;
    }

    this.stepIndex = resultIndex;
    Drawable drawable = drawables[resultIndex];
    imageView.setImageDrawable(drawable);
  }


  private int index = -1;//索引保存值

  /**
   * 根据 外部传入的进度值 直接更新索引
   */
  public void updateByProcessValue(@IntRange(from = 0, to = 100) int process) {

    int newIndex = process / step;
    if (index == newIndex) {
      return;
    }

    if (newIndex >= drawables.length) {
      return;
    }

    this.index = newIndex;

    imageView.setImageDrawable(drawables[index]);
  }
}
