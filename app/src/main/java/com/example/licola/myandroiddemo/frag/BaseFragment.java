package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.MyApplication;
import com.licola.llogger.LLogger;


/**
 * Created by 李可乐 on 2016/12/9 0009.
 */
public class BaseFragment extends Fragment {

  protected boolean isLife() {
    return false;
  }

  public BaseFragment() {
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isLife()) {
      LLogger.d(this);
    }
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    if (isLife()) {
      LLogger.d(this);
    }
    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (isLife()) {
      LLogger.d(this);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (isLife()) {
      LLogger.d(this);
    }
  }
}