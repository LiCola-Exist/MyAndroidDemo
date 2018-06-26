package com.example.licola.myandroiddemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.licola.llogger.LLogger;
import com.example.licola.myandroiddemo.utils.PixelUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimateFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";

  ViewGroup viewGroupTarget;
  View viewTarget;
  Button btnTarget;
  View viewTargetColor;

  private String mParam1;

  public AnimateFragment() {
  }

  public static AnimateFragment newInstance(String param1) {
    AnimateFragment fragment = new AnimateFragment();
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

  private int times = 0;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_animate, container, false);
    viewGroupTarget = rootView.findViewById(R.id.layout_group);
    viewTarget = rootView.findViewById(R.id.img_target);
    viewTargetColor = rootView.findViewById(R.id.img_target_color);
    btnTarget = rootView.findViewById(R.id.btn_target);

    Button button = rootView.findViewById(R.id.btn_start);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startAnimatorByTimes(times++);
      }
    });

    return rootView;
  }

  private void startAnimatorByTimes(int times) {
    viewAnimateScaleTranslation(times % 2 == 0, viewGroupTarget);

//    switch (times) {
//      case 0:
//        viewAnimateScaleTranslation(times%2==0,viewGroupTarget);
//        break;
//      case 1:
//        viewAnimateTranslation(viewTarget);
//        break;
//      case 2:
//        viewAnimateSpecific(btnTarget);
//        break;
//      case 3:
//        viewAnimateArgb(viewTargetColor);
//        break;
//      default:
//        this.times = 0;
//        break;
//    }
  }

  private void viewAnimateScaleTranslation(boolean isShrink, final ViewGroup viewGroup) {
    ValueAnimator animator;
    if (isShrink) {
      animator = ValueAnimator.ofFloat(0f, 1f);
    } else {
      animator = ValueAnimator.ofFloat(1f, 0f);
    }
    final float minValue = 0.3f;
    final float process = 1 - minValue;


    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        float processValue = value * process;
        float resultScale = 1 - processValue;
        LLogger.d("value:" + value + " resultScale:" + resultScale);
        viewGroup.setScaleX(resultScale);
        viewGroup.setScaleY(resultScale);

        int screenWidth = PixelUtils.getScreenWidth(getContext());
        int screenHeight = PixelUtils.getScreenHeight(getContext());
        float width = (screenWidth>>1)*minValue;
        viewGroup.setPivotX((screenWidth-24)*value);
        viewGroup.setPivotY((screenHeight-24)*value);
      }
    });
    animator.start();
  }

  private void viewAnimateArgb(View viewTargetColor) {
    ObjectAnimator animator = ObjectAnimator
        .ofInt(viewTargetColor, "BackgroundColor", 0xffff0000, 0xff00ff00);
    animator.setDuration(2000L);
    animator.setEvaluator(new ArgbEvaluator());
    animator.start();
  }

  private void viewAnimateTranslation(View view) {
    view.animate().translationXBy(300).withLayer();
  }

  private void viewAnimateSpecific(Button btnTarget) {
    ObjectAnimator animator = ObjectAnimator.ofInt(btnTarget, "width", 500).setDuration(500);
    animator.start();
  }
}
