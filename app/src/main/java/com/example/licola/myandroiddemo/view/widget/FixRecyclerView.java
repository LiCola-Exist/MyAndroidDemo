package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author LiCola
 * @date 2019-06-10
 */
public class FixRecyclerView extends RecyclerView {

  public FixRecyclerView(Context context) {
    super(context);
  }

  public FixRecyclerView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public FixRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    setAdapter(null);
  }
}
