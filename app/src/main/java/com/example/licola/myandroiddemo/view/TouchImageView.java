package com.example.licola.myandroiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by LiCola on 2017/11/1.
 */

public class TouchImageView extends android.support.v7.widget.AppCompatImageView {

  //内部保存的缩放方式 需要自己处理
  private ScaleType mScaleType;

  public TouchImageView(Context context) {
    this(context,null);
  }

  public TouchImageView(Context context,
      @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public TouchImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  private void init() {
    super.setScaleType(ScaleType.MATRIX);
  }

  @Override
  public void setImageDrawable(@Nullable Drawable drawable) {
    super.setImageDrawable(drawable);
    changeMatrix(drawable);
  }

  private final Matrix mBaseMatrix=new Matrix();
  private final Matrix mDrawMatrix = new Matrix();

  private void changeMatrix(Drawable drawable) {

    mBaseMatrix.reset();

    mBaseMatrix.postTranslate(100,100);
    mDrawMatrix.set(mBaseMatrix);

    setImageMatrix(mDrawMatrix);
  }

  @Override
  public void setScaleType(ScaleType scaleType) {
    this.mScaleType=scaleType;
  }
}
