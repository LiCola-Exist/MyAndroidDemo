package com.example.licola.myandroiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author LiCola
 * @date 2018/9/29
 */
public class TextCaptionView extends TextView {

  public TextCaptionView(Context context) {
    super(context);
  }

  public TextCaptionView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TextCaptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TextCaptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }
}
