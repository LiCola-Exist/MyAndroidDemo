package com.example.licola.myandroiddemo;

import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.example.licola.myandroiddemo.utils.Logger;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.example.licola.myandroiddemo.view.CanvasDemoView;
import com.example.licola.myandroiddemo.view.InterpolatorExplainView;
import com.example.licola.myandroiddemo.view.PaintDemoView;

/**
 * Created by 李可乐 on 2016/12/9 0009.
 */

public class ViewFragment extends Fragment {

  private static final String ARG_SECTION_KEY = "section_key";

  public ViewFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static ViewFragment newInstance(String key) {
    ViewFragment fragment = new ViewFragment();
    Bundle args = new Bundle();
    args.putString(ARG_SECTION_KEY, key);
    fragment.setArguments(args);
    return fragment;
  }

  int flag = 0;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_view, container, false);
    final LinearLayout layoutGroup =  rootView.findViewById(R.id.layout_group);
    final int screenHeight = PixelUtils.getScreenHeight(getContext());

    ViewGroup touch = rootView.findViewById(R.id.layout_touche);
    handleTouch(touch);

    final View canvasView = new CanvasDemoView(getContext());
    layoutGroup.addView(canvasView,
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight >> 1));

    final View drawableView = new InterpolatorExplainView(getContext());
    layoutGroup.addView(drawableView,
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight >> 1));

    drawableView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v instanceof InterpolatorExplainView) {
          TimeInterpolator interpolator = ((InterpolatorExplainView) v).getInterpolatorByFlag(flag);
          if (interpolator == null) {
            flag = 0;
            Toast.makeText(getContext(), "已经遍历到底", Toast.LENGTH_SHORT).show();
          } else {
            flag++;
            ((InterpolatorExplainView) v).startPathAnimator(interpolator);
          }
        }
      }
    });

    View paintView = new PaintDemoView(getContext());
    layoutGroup.addView(paintView,
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight >> 1));

    paintView.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        //长按复制出 ScrollView的内容 并显示
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(Color.GRAY);
        imageView.setImageBitmap(getScrollViewBitmap((NestedScrollView) rootView));
        layoutGroup.addView(imageView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        return true;
      }
    });

    paintView.setOnKeyListener(new OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
      }
    });

    return rootView;
  }

  private void handleTouch(ViewGroup touch) {
    touch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Logger.d("响应了 点击事件");
      }
    });

    touch.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Logger.d("ViewGroup Touch 拦截点击事件");
        return true;
      }
    });
  }

  public static Bitmap getScrollViewBitmap(NestedScrollView scrollView) {
    int height = 0;
    Bitmap bitmap;
    for (int i = 0; i < scrollView.getChildCount(); i++) {
      height += scrollView.getChildAt(i).getHeight();
    }
    bitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Config.ARGB_8888);
    final Canvas canvas = new Canvas(bitmap);
    scrollView.draw(canvas);
    return bitmap;
  }


}
