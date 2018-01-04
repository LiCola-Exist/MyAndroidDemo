package com.example.licola.myandroiddemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimateFragment extends Fragment {
  private static final String ARG_PARAM1 = "param1";

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

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  private int times=0;
  
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_animate, container, false);
    viewTarget = rootView.findViewById(R.id.img_target);
    viewTargetColor = rootView.findViewById(R.id.img_target_color);
    btnTarget = (Button) rootView.findViewById(R.id.btn_target);
    
    Button button = (Button) rootView.findViewById(R.id.btn_start);
    button.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        startAnimatorByTimes(times++);
      }
    });

    return rootView;
  }

  private void startAnimatorByTimes(int times) {
    switch (times){
      case 0:
        viewAnimateTranslation(viewTarget);
        break;
      case 1:
        viewAnimateSpecific(btnTarget);
        break;
      case 2:
        viewAnimateArgb(viewTargetColor);
        break;
      default:
        this.times=0;
        break;
    }
  }

  private void viewAnimateArgb(View viewTargetColor) {
    ObjectAnimator animator=ObjectAnimator.ofInt(viewTargetColor,"BackgroundColor",0xffff0000,0xff00ff00);
    animator.setDuration(2000L);
    animator.setEvaluator(new ArgbEvaluator());
    animator.start();
  }

  private void viewAnimateTranslation(View view){
    view.animate().translationXBy(300).withLayer();
  }
  
  private void viewAnimateSpecific(Button btnTarget) {
    ObjectAnimator animator = ObjectAnimator.ofInt(btnTarget, "width", 500).setDuration(500);
    animator.start();
  }
}
