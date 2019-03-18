package com.example.licola.myandroiddemo.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.view.widget.TouchView;

/**
 * A simple {@link Fragment} subclass. Use the {@link ViewTouchFragment#newInstance} factory method
 * to create an instance of this fragment.
 */
public class ViewTouchFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public ViewTouchFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_view_touch;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ViewTouchFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ViewTouchFragment newInstance(String param1) {
    ViewTouchFragment fragment = new ViewTouchFragment();
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

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View viewRoot = super.onCreateView(inflater,container ,savedInstanceState );

    TouchView touchView= viewRoot.findViewById(R.id.view_touch_target);


    return viewRoot;
  }

}
