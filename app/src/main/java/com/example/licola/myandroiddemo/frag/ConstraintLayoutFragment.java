package com.example.licola.myandroiddemo.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConstraintLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConstraintLayoutFragment extends BaseFragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  public ConstraintLayoutFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_constraint_layout;
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ConstraintLayoutFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ConstraintLayoutFragment newInstance(String param1) {
    ConstraintLayoutFragment fragment = new ConstraintLayoutFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

}
