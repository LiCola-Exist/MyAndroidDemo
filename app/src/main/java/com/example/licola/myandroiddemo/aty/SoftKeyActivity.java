package com.example.licola.myandroiddemo.aty;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;

public class SoftKeyActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_soft_key);

    EditText editText = findViewById(R.id.et_input);

    editText.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        LLogger.d(hasFocus);
      }
    });

    NestedScrollView scrollView = findViewById(R.id.layout_scroll);
//    scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
//      @Override
//      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
//          int oldScrollY) {
//        LLogger.d(scrollX, oldScrollX, scrollY, oldScrollY);
//      }
//    });
  }
}
