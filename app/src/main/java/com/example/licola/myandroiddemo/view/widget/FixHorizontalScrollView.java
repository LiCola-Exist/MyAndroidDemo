package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * @author LiCola
 * @date 2019-05-05
 */
public class FixHorizontalScrollView extends HorizontalScrollView {

  private onScrollChangedListener onScrollChangedListener;

  public FixHorizontalScrollView(Context context) {
    super(context);
  }

  public FixHorizontalScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FixHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public FixHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setOnScrollChangedListener(
      FixHorizontalScrollView.onScrollChangedListener onScrollChangedListener) {
    this.onScrollChangedListener = onScrollChangedListener;
  }

  @Override
  public void computeScroll() {
    super.computeScroll();
  }

  @Override
  protected void onScrollChanged(int x, int y, int oldX, int oldY) {
    super.onScrollChanged(x, y, oldX, oldY);
    if (onScrollChangedListener != null) {
      onScrollChangedListener.onScrollChange(x, y, oldX, oldY);
    }
  }

  public interface onScrollChangedListener {

    void onScrollChange(int x, int y, int oldX, int oldY);
  }
}
