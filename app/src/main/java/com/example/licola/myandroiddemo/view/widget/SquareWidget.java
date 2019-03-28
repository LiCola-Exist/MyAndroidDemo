package com.example.licola.myandroiddemo.view.widget;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author LiCola
 * @date 2019/3/20
 */
public interface SquareWidget {

  float DEFAULT_RATIO = 1.0f;
  int FIX_SIDE_WIDTH = 0;
  int FIX_SIDE_HEIGHT = 1;

  @IntDef({FIX_SIDE_WIDTH, FIX_SIDE_HEIGHT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface FixSide {

  }

  void setRatio(float ratio);

  void setRatio(float ratio, @FixSide int fixSide);
}
