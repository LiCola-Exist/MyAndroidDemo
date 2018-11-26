package com.example.licola.myandroiddemo.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.java.xml.XmlHandler;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass. Use the {@link XmlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XmlFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";

  private String mParam1;

  private TextView txtResult;

  public XmlFragment() {
    // Required empty public constructor
  }


  public static XmlFragment newInstance(String param1) {
    XmlFragment fragment = new XmlFragment();
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
    View viewRoot = inflater.inflate(R.layout.fragment_xml, container, false);
    txtResult = (TextView) viewRoot.findViewById(R.id.txt_result);
    return viewRoot;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    try {
      XmlHandler.main(getContext());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
