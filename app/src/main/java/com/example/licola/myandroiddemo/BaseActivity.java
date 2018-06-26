package com.example.licola.myandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2017/6/8.
 */

public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LLogger.d(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    LLogger.d(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    LLogger.d(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    LLogger.d(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    LLogger.d(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LLogger.d(this);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    LLogger.d(this);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
  }
}
