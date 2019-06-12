package com.example.licola.myandroiddemo.view;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author LiCola
 * @date 2019/2/21
 */
public class CustomLayoutManager extends LayoutManager {

  @Override
  public LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
  }

  private int mTotalHeight = 0;

  @Override
  public void onLayoutChildren(Recycler recycler, State state) {
    int offsetY = 0;
    for (int i = 0; i < getItemCount(); i++) {
      View view = recycler.getViewForPosition(i);
      addView(view);
      measureChildWithMargins(view, 0, 0);
      int measuredWidth = getDecoratedMeasuredWidth(view);
      int measuredHeight = getDecoratedMeasuredHeight(view);
      layoutDecorated(view, 0, offsetY, measuredWidth, measuredHeight + offsetY);
      offsetY += measuredHeight;
    }

    mTotalHeight = Math.max(offsetY, getVerticalSpace());

  }

  private int getVerticalSpace() {
    return getHeight() - getPaddingBottom() - getPaddingTop();
  }

  @Override
  public boolean canScrollVertically() {
    return true;
  }

  private int mSumDy = 0;

  /**
   * 当手指由下往上滑时,dy>0 当手指由上往下滑时,dy<0
   */
  @Override
  public int scrollVerticallyBy(int dy, Recycler recycler, State state) {

    int travel = dy;
    if (mSumDy + dy < 0) {
      //滑动到顶部
      travel = -mSumDy;
    } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
      //滑动到底部
      travel = mTotalHeight - getVerticalSpace() - mSumDy;
    }

    mSumDy += travel;

    offsetChildrenVertical(-travel);
    return dy;
  }

  @Override
  public void collectInitialPrefetchPositions(int adapterItemCount,
      LayoutPrefetchRegistry layoutPrefetchRegistry) {
    super.collectInitialPrefetchPositions(adapterItemCount, layoutPrefetchRegistry);
  }
}
