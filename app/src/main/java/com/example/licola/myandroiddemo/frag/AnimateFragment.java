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
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
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

    SeekBar seekBar = rootView.findViewById(R.id.seekBar);
    Button btnSeek = rootView.findViewById(R.id.btn_seek_run);
    btnSeek.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//        runSeek(seekBar);
        animatorSeek(seekBar);
      }
    });

    LottieAnimationView lottieView = rootView.findViewById(R.id.animation_view);
    lottieView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        lottieView.setImageAssetsFolder("images");
        lottieView.setAnimation("WavesAnimation.json");

        animatorLottie(lottieView);
      }
    });

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
    rootView.findViewById(R.id.tv_target_alpha).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateAlpha((TextView)v);
      }
    });

    rootView.findViewById(R.id.iv_step_target).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        v.post(new Runnable() {
          @Override
          public void run() {
            animateStep(v);
          }
        });
        v.post(new Runnable() {
          @Override
          public void run() {
            animateStep(v);
          }
        });
//        v.postOnAnimation(new Runnable() {
//          @Override
//          public void run() {
//            LLogger.d();
//          }
//        });
//
//        v.post()
      }
    });

    return rootView;
  }

  private void animateAlpha(TextView textView) {
    ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 3f, 0f);
    animator.setRepeatMode(ValueAnimator.RESTART);
    animator.setRepeatCount(5);
    animator.setDuration(4000L);
    animator.setInterpolator(new FastOutLinearInInterpolator());
    animator.start();
  }


  private void runSeek(SeekBar seekBar) {

    long gap=20;

    final Runnable runnable = new Runnable() {
      @Override
      public void run() {
        if (seekBar.getProgress() == 100) {
          seekBar.setProgress(0);
        }
        seekBar.incrementProgressBy(1);
        seekBar.postDelayed(this, gap);
      }
    };

    seekBar.postDelayed(runnable, gap);
  }

  private void animatorSeek(SeekBar seekBar){

    ValueAnimator animator=ValueAnimator.ofInt(0,20)
        .setDuration(1000);
    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int value= (int) animation.getAnimatedValue();
        LLogger.d(value);
        seekBar.setProgress(value);
      }
    });

    animator.start();
  }


  private void playLottie(LottieAnimationView lottieView) {
    lottieView.setRepeatCount(LottieDrawable.INFINITE);
    lottieView.playAnimation();
  }

  private void animatorLottie(LottieAnimationView lottieView) {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1)
        .setDuration(3000);

    valueAnimator.setInterpolator(new FastOutSlowInInterpolator());

    valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        lottieView.setProgress(value);
      }
    });

    valueAnimator.start();
  }


  private void animateStep(View targetView) {

    int height = targetView.getHeight() / 4;
    targetView.animate()
        .setDuration(400)
        .translationYBy(-height)
        .start();
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
