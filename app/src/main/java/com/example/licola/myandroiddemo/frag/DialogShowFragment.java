package com.example.licola.myandroiddemo.frag;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;


/**
 * A simple {@link Fragment} subclass. Use the {@link DialogShowFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class DialogShowFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  public DialogShowFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment DialogOperateFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DialogShowFragment newInstance(String param1) {
    DialogShowFragment fragment = new DialogShowFragment();
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
    View rootView = inflater.inflate(R.layout.fragment_dialog_operate, container, false);
    bindAlertDialog(rootView);
    bindSimpleDialog(rootView);
    return rootView;
  }

  private void bindAlertDialog(View rootView) {
    rootView.findViewById(R.id.btn_dialog_alert).setOnClickListener(v -> {
      new AlertDialog.Builder(getContext()).setTitle("标题").setMessage("内容").setNegativeButton(
          "消极按钮", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
          })
          .show();
    });
  }

  private void bindSimpleDialog(View rootView) {
    SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();

    rootView.findViewById(R.id.btn_dialog_fragment).setOnClickListener(view -> {

      FragmentManager fragmentManager = getFragmentManager();
      simpleDialogFragment.show(fragmentManager, SimpleDialogFragment.TAG);
    });
  }

  public static class SimpleDialogFragment extends BaseDialogFragment {

    public static final String TAG = SimpleDialogFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
      return R.layout.fragment_dialog_simple;
    }

    @Override
    protected void onConfigDialogWindow(Dialog dialog) {
      super.onConfigDialogWindow(dialog);
      dialog.setCancelable(true);
      dialog.setCanceledOnTouchOutside(false);
    }

    @OnClick(R.id.tv_dialog_cancel)
    public void onDialogCancelClick(View view) {
      dismiss();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
      LLogger.d(this.toString(),isAdded(),isInLayout(),isMenuVisible(),isVisible());
      super.show(manager, tag);
    }
  }

}
