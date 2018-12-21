package com.example.licola.myandroiddemo.aty;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.WindowsController;
import com.example.licola.myandroiddemo.view.TouchLinearLayout;
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
    layoutGroup.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean newState = !layoutGroup.isSelected();
        Toast.makeText(mContext, "点击容器:" + newState, Toast.LENGTH_SHORT).show();
        layoutGroup.setInterceptTouch(newState);
        layoutGroup.setSelected(newState);
      }
    });

    NestedScrollView scrollView = findViewById(R.id.layout_scroll);
    scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
          int oldScrollY) {
//        LLogger.d(scrollX, oldScrollX, scrollY, oldScrollY);
      }
    });
  }
}
