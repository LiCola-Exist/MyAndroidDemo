package com.example.licola.myandroiddemo;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass. Use the {@link ToastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToastFragment extends BaseFragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  private String mParam1;


  public ToastFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ToastFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ToastFragment newInstance(String param1) {
    ToastFragment fragment = new ToastFragment();
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
    View rootView = inflater.inflate(R.layout.fragment_toast, container, false);

    onBindNormalToast(rootView.findViewById(R.id.btn_toast_normal));
    onBindGravityToast(rootView.findViewById(R.id.btn_toast_gravity));
    onBindViewToast(rootView.findViewById(R.id.btn_toast_view));
    onBindViewPopup(rootView.findViewById(R.id.btn_popup_view));
    return rootView;
  }

  private void onBindViewPopup(final View view) {

    LayoutInflater inflate = (LayoutInflater)
        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View toastView = inflate.inflate(R.layout.toast_view, null);
    TextView tvContent = toastView.findViewById(R.id.tv_content);
    tvContent.setText("自定义View的Popup");

    final PopupWindow popupWindow = new PopupWindow(toastView, LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT);

    popupWindow.setOutsideTouchable(true);

    view.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        if (popupWindow.isShowing()) {
          popupWindow.dismiss();
        } else {
          popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
      }
    });

  }

  private void onBindViewToast(View btnView) {
    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflate = (LayoutInflater)
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflate.inflate(R.layout.toast_view, null);
        TextView tvContent = toastView.findViewById(R.id.tv_content);
        tvContent.setText("自定义View的Toast");
        toast.setView(toastView);
        toast.show();
      }
    });

  }


  private void onBindGravityToast(View btnView) {
    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast toast = Toast.makeText(getContext(), "内容", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    });
  }

  private void onBindNormalToast(View btnView) {
    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "内容", Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

}
