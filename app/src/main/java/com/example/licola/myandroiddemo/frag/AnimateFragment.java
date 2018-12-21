package com.example.licola.myandroiddemo.frag;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.licola.llogger.LLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Use the {@link AnimateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimateFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";


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


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_animate, container, false);

    final ViewGroup viewGroupTarget = rootView.findViewById(R.id.layout_group_shrink);

    viewGroupTarget.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        boolean newState = !v.isSelected();
        viewGroupTarget.setSelected(newState);
        viewAnimateScaleTranslation(newState, v);
      }
    });

    rootView.findViewById(R.id.img_target).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        viewAnimateTranslation(v);
      }
    });

    rootView.findViewById(R.id.btn_target).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        viewAnimateSpecific((Button) v);
      }
    });
    rootView.findViewById(R.id.img_target_color).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        viewAnimateArgb(v);
      }
    });
    rootView.findViewById(R.id.iv_target_drawable).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateDrawable((ImageView) v);
      }
    });

    rootView.findViewById(R.id.iv_step_target).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateStep(v);
      }
    });

    return rootView;
  }

  private void animateStep(View targetView) {
    int height = targetView.getHeight()/4;
    targetView.animate()
        .translationYBy(height)
        .withLayer();
  }

  private void animateDrawable(ImageView imageView) {
    AnimationDrawable viewDrawable = (AnimationDrawable) imageView.getDrawable();
    if (viewDrawable == null) {
      viewDrawable = makeAnimationDrawable();
      imageView.setImageDrawable(viewDrawable);
      viewDrawable.start();
    } else {
      if (viewDrawable.isRunning()) {
        viewDrawable.stop();
      } else {
        viewDrawable.start();
      }
    }

  }

  @NonNull
  private AnimationDrawable makeAnimationDrawable() {
    AnimationDrawable animationDrawable = new AnimationDrawable();
    int[] resIds = new int[]{
        R.drawable.a01,
        R.drawable.a05,
        R.drawable.a10,
        R.drawable.a15,
        R.drawable.a20,
        R.drawable.a25,
        R.drawable.a30,
        R.drawable.a35,
        R.drawable.a40,
        R.drawable.a45,
        R.drawable.a50,
    };
    for (int resId : resIds) {
      Drawable frame = ContextCompat.getDrawable(getContext(), resId);
      animationDrawable.addFrame(frame, 200);
    }
    animationDrawable.setOneShot(true);
    return animationDrawable;
  }

  private void viewAnimateScaleTranslation(boolean isShrink, final View targetView) {
    ValueAnimator animator;
    if (isShrink) {
      animator = ValueAnimator.ofFloat(0f, 1f);
    } else {
      animator = ValueAnimator.ofFloat(1f, 0f);
    }
    final float minValue = 0.3f;
    final float process = 1 - minValue;

    animator.setRepeatCount(1);
    animator.setRepeatMode(ValueAnimator.RESTART);
    animator.addListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        LLogger.d();
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        LLogger.d();
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        LLogger.d();
      }

      @Override
      public void onAnimationRepeat(Animator animation) {
        LLogger.d();
      }
    });
    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        float processValue = value * process;
        float resultScale = 1 - processValue;
//        LLogger.d("value:" + value + " resultScale:" + resultScale);
        targetView.setScaleX(resultScale);
        targetView.setScaleY(resultScale);

        int screenWidth = PixelUtils.getScreenWidth(getContext());
        int screenHeight = PixelUtils.getScreenHeight(getContext());
        float width = (screenWidth >> 1) * minValue;
        targetView.setPivotX((screenWidth - 24) * value);
        targetView.setPivotY((screenHeight - 24) * value);
      }
    });
    animator.start();
  }

  private void viewAnimateTranslation(final View view) {
    try {
      List<File> files = LLogger.fetchLogList(0);
      LLogger.d(files);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    view.setAlpha(0.3f);
    view.animate()
        .alpha(1f)
        .scaleXBy(2)
        .scaleYBy(2)
        .translationXBy(200)
//        .translationYBy(200)
        .setDuration(2000L)
        .withLayer();
  }

  private void viewAnimateArgb(View viewTargetColor) {
    ObjectAnimator animator = ObjectAnimator
        .ofInt(viewTargetColor, "BackgroundColor", 0xffff0000, 0xff00ff00);
    animator.setDuration(2000L);
    animator.setEvaluator(new ArgbEvaluator());
    animator.start();
  }

  private void viewAnimateSpecific(Button btnTarget) {
    int oldWidth = btnTarget.getWidth();
    ObjectAnimator animator = ObjectAnimator.ofInt(btnTarget, "width", oldWidth + 200)
        .setDuration(1500);
    animator.start();
  }
}
