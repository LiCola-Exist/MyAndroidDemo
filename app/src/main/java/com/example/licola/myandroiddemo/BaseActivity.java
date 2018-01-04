package com.example.licola.myandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.example.licola.myandroiddemo.utils.Logger;
import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.util.Map;

/**
 * Created by LiCola on 2017/6/8.
 */

public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Class<?> tClass=this.getClass();
    Annotation[] annotations= tClass.getAnnotations();

  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }
}
