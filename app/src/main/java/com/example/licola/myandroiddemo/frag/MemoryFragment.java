package com.example.licola.myandroiddemo.frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.OnClick;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.aty.MemoryActivity;
import com.licola.llogger.LLogger;

/**
 * A simple {@link Fragment} subclass. Use the {@link MemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoryFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_memory;
  }

  public MemoryFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment MemoryFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static MemoryFragment newInstance(String param1) {
    MemoryFragment fragment = new MemoryFragment();
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
    View rootView = super.onCreateView(inflater, container, savedInstanceState);

    return rootView;
  }


  @OnClick(R.id.btn_new_objects)
  public void onMakeNewObjectsClick(View view) {
    int size = 1000;

    for (int i = 0; i < size; i++) {
      View viewObject = new ImageView(getMContext());
    }

//    Runtime.getRuntime().gc();
  }

  @OnClick(R.id.btn_entry_aty)
  public void onEntyAtyClick(View view) {
    startActivity(new Intent(getMContext(), MemoryActivity.class));
  }

  @OnClick(R.id.btn_new_objects_continuous)
  public void onMakeNewObjectsContinuousClick(View view) {

    int size = 20;

    int sizeDeep = 100;

    try {
      View[] views = new View[size];

      for (int i = 0; i < size; i++) {

        for (int j = 0; j < sizeDeep; j++) {
          views[i] = new ImageView(getMContext());
        }
        Thread.sleep(1000);

        LLogger.d("一次创建");
//        Runtime.getRuntime().gc();

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
