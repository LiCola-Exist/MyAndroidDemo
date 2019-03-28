package com.example.licola.myandroiddemo.frag;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.view.widget.TouchView;
import com.licola.llogger.LLogger;

/**
 * A simple {@link Fragment} subclass. Use the {@link ViewTouchFragment#newInstance} factory method
 * to create an instance of this fragment.
 */
public class ViewTouchFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public ViewTouchFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_view_touch;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ViewTouchFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ViewTouchFragment newInstance(String param1) {
    ViewTouchFragment fragment = new ViewTouchFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View viewRoot = super.onCreateView(inflater,container ,savedInstanceState );

    LinearLayout layoutGroup= viewRoot.findViewById(R.id.layout_group);
    TouchView touchView= viewRoot.findViewById(R.id.view_touch_target);

    handleTouch((NestedScrollView) viewRoot,layoutGroup, touchView);


    return viewRoot;
  }

  private void handleTouch(final NestedScrollView rootView,LinearLayout layoutGroup, TouchView touch) {
    touch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        LLogger.d("响应了 点击事件");
      }
    });

    touch.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        //长按复制出 ScrollView的内容 并显示
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(Color.GRAY);
        Bitmap screenShotBitmap = getScrollViewBitmap(rootView);
        imageView.setImageBitmap(screenShotBitmap);

        layoutGroup.addView(imageView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        return true;
      }
    });

//    touch.setOnTouchListener(new OnTouchListener() {
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        LLogger.d("ViewGroup Touch 拦截点击事件");
//
//        try {
//          Thread.sleep(100000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//
//        return true;
//      }
//    });
  }


  public static Bitmap getScrollViewBitmap(NestedScrollView scrollView) {
    int height = 0;
    Bitmap bitmap;
    for (int i = 0; i < scrollView.getChildCount(); i++) {
      height += scrollView.getChildAt(i).getHeight();
    }
    bitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Config.ARGB_8888);
    final Canvas canvas = new Canvas(bitmap);
    //用view绘制到canvas画布上
    scrollView.draw(canvas);
    //返回有内容的bitmap
    return bitmap;
  }

}
