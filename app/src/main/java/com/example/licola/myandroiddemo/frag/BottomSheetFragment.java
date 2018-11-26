package com.example.licola.myandroiddemo.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.aty.BottomSheetActivity;

/**
 * Created by 李可乐 on 2016/12/9 0009.
 */

public class BottomSheetFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_KEY = "section_key";

    public BottomSheetFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BottomSheetFragment newInstance(String key) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) rootView.findViewById(R.id.btn_bottom_sheet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BottomSheetActivity.class);
                startActivity(intent);
            }
        });

        Button btnImage = (Button) rootView.findViewById(R.id.btn_image_select);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

}
