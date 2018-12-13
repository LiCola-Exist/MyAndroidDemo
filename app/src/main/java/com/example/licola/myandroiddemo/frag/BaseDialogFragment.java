package com.example.licola.myandroiddemo.frag;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.PixelUtils;

/**
 * @author LiCola
 * @date 2018/8/20
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {

  private static final double SCREEN_WIDTH_PERCENT = 0.7f;


  protected Context mContext;

  protected View viewRoot;

  private Unbinder mUnbinder;


  protected abstract int getLayoutId();

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.mContext = context;
  }

  @Override
  public void onStart() {
    super.onStart();
    onConfigDialogWindow(getDialog());
  }

  protected void onConfigDialogWindow(Dialog dialog) {
    Window window = dialog.getWindow();
    if (window == null) {
      return;
    }
    //设置dialog的百分宽度
    window.setLayout((int) (PixelUtils.getScreenWidth(mContext) * SCREEN_WIDTH_PERCENT),
        LayoutParams.WRAP_CONTENT);
    //透明底色圆角 为了上层的View的圆角
    window.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.transparent));
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    viewRoot = inflater.inflate(getLayoutId(), container, false);
    mUnbinder = ButterKnife.bind(this, viewRoot);
    return viewRoot;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ((ViewGroup) (viewRoot.getParent())).removeView(viewRoot);
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }
  }

  public void onFinish() {
    FragmentActivity activity = getActivity();
    if (activity == null) {
      return;
    }
    activity.finish();
  }

}
