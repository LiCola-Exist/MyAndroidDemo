package com.example.licola.myandroiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by 李可乐 on 2016/12/9 0009.
 */
public class BaseFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public BaseFragment() {
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static BaseFragment newInstance(String key) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, key);
        fragment.setArguments(args);
        return fragment;
    }
}