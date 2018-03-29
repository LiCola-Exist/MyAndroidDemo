package com.example.licola.myandroiddemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleFragment extends Fragment {

  View viewRoot;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public ModuleFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ModuleFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ModuleFragment newInstance(String param1) {
    ModuleFragment fragment = new ModuleFragment();
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
    viewRoot = inflater.inflate(R.layout.fragment_module, container, false);

    return viewRoot;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewRoot.findViewById(R.id.btn_send_broad).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        LocalBroadcastManager.getInstance(getActivity())
            .sendBroadcast(new Intent(Intent.ACTION_PICK_ACTIVITY));
      }
    });
  }
}
