package com.example.licola.myandroiddemo.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.OrientationEventListener;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;

/**
 * A simple {@link Fragment} subclass. Use the {@link ConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public ConfigFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_config;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ConfigFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ConfigFragment newInstance(String param1) {
    ConfigFragment fragment = new ConfigFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  private OrientationEventListener orientationEventListener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }

    orientationEventListener = new OrientationEventListener(getContext()) {
      @Override
      public void onOrientationChanged(int orientation) {
        if (OrientationEventListener.ORIENTATION_UNKNOWN==orientation){
          //手机平放时，检测不到有效的角度
        }
        LLogger.d(orientation);
      }
    };

    orientationEventListener.enable();

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (orientationEventListener != null) {
      orientationEventListener.disable();
    }
  }
}
