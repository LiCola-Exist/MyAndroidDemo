package com.example.licola.myandroiddemo.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;


/**
 * A simple {@link Fragment} subclass. Use the {@link TextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public TextFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment TextFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static TextFragment newInstance(String param1) {
    TextFragment fragment = new TextFragment();
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
    View rootView = inflater.inflate(R.layout.fragment_text, container, false);

    final TextView tvChinese = rootView.findViewById(R.id.tv_chinese);
    tvChinese.post(new Runnable() {
      @Override
      public void run() {
        LLogger.d(tvChinese.getTextSize(),tvChinese.getWidth(),tvChinese.getHeight());
      }
    });
    final TextView tvChar = rootView.findViewById(R.id.tv_char);
    tvChar.post(new Runnable() {
      @Override
      public void run() {
        LLogger.d(tvChar.getTextSize(),tvChar.getWidth(),tvChar.getHeight());
      }
    });

    return rootView;
  }

}
