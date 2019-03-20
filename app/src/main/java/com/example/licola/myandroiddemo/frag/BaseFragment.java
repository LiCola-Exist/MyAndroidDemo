package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.licola.llogger.LLogger;


/**
 * Created by 李可乐 on 2016/12/9 0009.
 */
public abstract class BaseFragment extends Fragment {

  private static final boolean DEBUG = false;

  private static final String Life = "FragLife:";

  protected Context mContext;

  protected View viewRoot;

  private Unbinder mUnbinder;

  public BaseFragment() {
  }

  protected abstract int getLayoutId();

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (DEBUG) {
      LLogger.d(this);
    }
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    viewRoot = inflater.inflate(getLayoutId(), container, false);
    mUnbinder = ButterKnife.bind(this, viewRoot);
    if (DEBUG) {
      LLogger.d(this);
    }
    return viewRoot;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (DEBUG) {
      LLogger.d(this);
    }
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }
    viewRoot = null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (DEBUG) {
      LLogger.d(this);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (DEBUG) {
      LLogger.d(this);
    }
  }
}