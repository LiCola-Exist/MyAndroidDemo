package com.example.licola.myandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.example.licola.myandroiddemo.utils.Logger;

/**
 * Created by LiCola on 2017/6/8.
 */

public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Logger.d(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Logger.d(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    Logger.d(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Logger.d(this);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Logger.d(this);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
  }


}
