package com.example.licola.myandroiddemo.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;

/**
 * Created by LiCola on 2018/6/21.
 */
public class NestedScrollFragment extends BaseFragment {

  @Override
  protected boolean isLife() {
    return true;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View rootView = inflater.inflate(R.layout.content_scrolling, container, false);
    return rootView;
  }
}
