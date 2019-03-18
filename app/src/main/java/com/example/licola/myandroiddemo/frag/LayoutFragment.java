package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.aty.LayoutActivity;


public class LayoutFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public LayoutFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_layout;
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment LayoutFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static LayoutFragment newInstance(String param1) {
    LayoutFragment fragment = new LayoutFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View viewRoot = super.onCreateView(inflater,container ,savedInstanceState );

    viewRoot.findViewById(R.id.tv_layout_linear).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        LayoutActivity.start(getContext(), R.layout.activity_linear);
      }
    });

    viewRoot.findViewById(R.id.tv_layout_relative).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        LayoutActivity.start(getContext(), R.layout.activity_relative);
      }
    });

    return viewRoot;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

  }

  @Override
  public void onDetach() {
    super.onDetach();
  }


}
