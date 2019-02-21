package com.example.licola.myandroiddemo.aty;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.SoftKeyboardHelper;
import com.example.licola.myandroiddemo.utils.SoftKeyboardHelper.OnSoftKeyboardListener;
import com.example.licola.myandroiddemo.utils.WindowsController;
import com.example.licola.myandroiddemo.view.widget.TouchLinearLayout;
import com.licola.llogger.LLogger;

public class SoftKeyActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    WindowsController.setTranslucentWindows(this);
    setContentView(R.layout.activity_soft_key);

    EditText editText = findViewById(R.id.et_input);

    editText.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        LLogger.d(hasFocus);
      }
    });

    TouchLinearLayout layoutGroup = findViewById(R.id.layout_group);
    layoutGroup.setInterceptTouch(false);
//    layoutGroup.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        boolean newState = !layoutGroup.isSelected();
//        Toast.makeText(mContext, "点击容器:" + newState, Toast.LENGTH_SHORT).show();
//        layoutGroup.setInterceptTouch(newState);
//        layoutGroup.setSelected(newState);
//      }
//    });

    View contentView = findViewById(android.R.id.content);
    NestedScrollView scrollView = findViewById(R.id.layout_scroll);

    SoftKeyboardHelper.setOnSoftKeyboardHeight(this, new OnSoftKeyboardListener() {

      @Override
      public void onChange(int appHeight, int softHeight, int navBarHeight) {

        boolean openSoft = softHeight > 0;

        LLogger.d(openSoft, appHeight, softHeight, navBarHeight);
        if (openSoft) {
          scrollView.post(new Runnable() {
            @Override
            public void run() {
              int oldHeight = scrollView.getHeight();
              scrollView.getLayoutParams().height = oldHeight - softHeight - navBarHeight;
              scrollView.requestLayout();
            }
          });
        } else {
          scrollView.post(new Runnable() {
            @Override
            public void run() {
              scrollView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
              scrollView.requestLayout();

              int oldScrollY = scrollView.getScrollY();
              scrollView.scrollBy(0, -oldScrollY);
            }
          });
        }
      }
    });

    scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
          int oldScrollY) {
//        LLogger.d(scrollX, oldScrollX, scrollY, oldScrollY);
      }
    });
  }
}
