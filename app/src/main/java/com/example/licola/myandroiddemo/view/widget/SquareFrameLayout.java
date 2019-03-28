package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.example.licola.myandroiddemo.R;

/**
 * Created by 李可乐 on 2016/10/16 0016. 能够设置比例的FrameLayout 示例：{ app:frame_ratio="1.778"(默认比例为：1.0f)
 * app:frame_fix_side="width"(默认固定边:width) } 效果：固定width宽为基础，比例计算高height(计算边)为高(固定边)的1.778倍(乘)，即高/宽=16/9
 * 特殊处理，当计算边的值大于父容器限定值，将逆转，以限定边为固定边，反向计算(除)，仍旧保持比例。
 *
 * 重写onMeasure方法公式为： final = (int) (fix * ratio)
 */
public class SquareFrameLayout extends FrameLayout implements SquareWidget {

  private static final float DEFAULT_RATIO = 1.0f;
  private static final int FIX_SIDE_WIDTH = 0;
  private static final int FIX_SIDE_HEIGHT = 1;

  private float ratio;
  private int fixSide;

  public SquareFrameLayout(@NonNull Context context) {
    super(context);
  }

  public SquareFrameLayout(@NonNull Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SquareFrameLayout(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.SquareFrameLayout);
    ratio = typedArray.getFloat(R.styleable.SquareFrameLayout_frame_ratio, DEFAULT_RATIO);
    fixSide = typedArray.getInt(R.styleable.SquareFrameLayout_frame_fix_side, FIX_SIDE_WIDTH);

    typedArray.recycle();
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
    int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

    int originalWidthMode = MeasureSpec.getMode(widthMeasureSpec);
    int originalHeightMode = MeasureSpec.getMode(heightMeasureSpec);

    int finalWidth = 0;
    int finalHeight = 0;

    if (fixSide == FIX_SIDE_WIDTH) {
      finalWidth = originalWidth;
      finalHeight = (int) (finalWidth * ratio+0.5f);
      if (originalHeightMode != MeasureSpec.UNSPECIFIED && finalHeight > originalHeight) {
        //计算边 非无限定 且 大于限定值
        finalHeight = originalHeight;
        finalWidth = (int) (finalHeight / ratio+0.5f);
      }

    } else if (fixSide == FIX_SIDE_HEIGHT) {
      finalHeight = originalHeight;
      finalWidth = (int) (finalHeight * ratio+0.5f);
      if (originalWidthMode != MeasureSpec.UNSPECIFIED && finalWidth > originalWidth) {
        finalWidth = originalWidth;
        finalHeight = (int) (finalWidth / ratio+0.5f);
      }
    }

    super.onMeasure(MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
  }

  @Override
  public void setRatio(float ratio) {
    this.ratio = ratio;
    requestLayout();
  }

  @Override
  public void setRatio(float ratio, int fixSide) {
    this.ratio = ratio;
    this.fixSide = fixSide;
    requestLayout();
  }
}
