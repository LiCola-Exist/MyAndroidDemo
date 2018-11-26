package com.example.licola.myandroiddemo.frag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.view.ConditionSeekBar;
import com.example.licola.myandroiddemo.view.ConditionSeekBar.OnProcessChangeListener;
import com.example.licola.myandroiddemo.view.ImageProgressView;
import com.licola.llogger.LLogger;
import io.apptik.widget.MultiSlider;
import io.apptik.widget.MultiSlider.OnThumbValueChangeListener;
import io.apptik.widget.MultiSlider.OnTrackingChangeListener;
import io.apptik.widget.MultiSlider.Thumb;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProcessViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessViewFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";

  ImageView imageView;
  TextView textView;
  ConditionSeekBar seekBar;
  ImageProgressView imageProgressView;

  int[] drawableRedIds = new int[]{
      R.drawable.a01,
      R.drawable.a02,
      R.drawable.a03,
      R.drawable.a04,
      R.drawable.a05,
      R.drawable.a06,
      R.drawable.a07,
      R.drawable.a08,
      R.drawable.a09,
      R.drawable.a10,
      R.drawable.a11,
      R.drawable.a12,
      R.drawable.a13,
      R.drawable.a14,
      R.drawable.a15,
      R.drawable.a16,
      R.drawable.a17,
      R.drawable.a18,
      R.drawable.a19,
      R.drawable.a20,
      R.drawable.a21,
      R.drawable.a22,
      R.drawable.a23,
      R.drawable.a24,
      R.drawable.a25,
      R.drawable.a26,
      R.drawable.a27,
      R.drawable.a28,
      R.drawable.a29,
      R.drawable.a30,
      R.drawable.a31,
      R.drawable.a32,
      R.drawable.a33,
      R.drawable.a34,
      R.drawable.a35,
      R.drawable.a36,
      R.drawable.a37,
      R.drawable.a38,
      R.drawable.a39,
      R.drawable.a40,
      R.drawable.a41,
      R.drawable.a42,
      R.drawable.a43,
      R.drawable.a44,
      R.drawable.a45,
      R.drawable.a46,
      R.drawable.a47,
      R.drawable.a48,
      R.drawable.a49,
      R.drawable.a50,
  };

  private String mParam1;

  public ProcessViewFragment() {
  }


  public static ProcessViewFragment newInstance(String param1) {
    ProcessViewFragment fragment = new ProcessViewFragment();
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
    View viewRoot = inflater.inflate(R.layout.fragment_process_view, container, false);
    bindRangeProgress(viewRoot);
    bindDrawableProcess(viewRoot);

    return viewRoot;
  }

  private void bindRangeProgress(View viewRoot) {
    MultiSlider slider=viewRoot.findViewById(R.id.range_slider);
    slider.setOnThumbValueChangeListener(new OnThumbValueChangeListener() {
      @Override
      public void onValueChanged(MultiSlider multiSlider, Thumb thumb, int thumbIndex, int value) {
        LLogger.d(thumbIndex,value);
      }
    });

    slider.setOnTrackingChangeListener(new OnTrackingChangeListener() {
      @Override
      public void onStartTrackingTouch(MultiSlider multiSlider, Thumb thumb, int value) {
      }

      @Override
      public void onStopTrackingTouch(MultiSlider multiSlider, Thumb thumb, int value) {

      }
    });
  }

  private void bindDrawableProcess(View viewRoot) {
    imageView = ButterKnife.findById(viewRoot, R.id.img_target);
    textView = ButterKnife.findById(viewRoot, R.id.txt_result);
    seekBar = ButterKnife.findById(viewRoot, R.id.seekBar);

    int size = drawableRedIds.length;
    Drawable[] drawables = new Drawable[size];
    Options options = new Options();
    options.inSampleSize = 2;
    for (int i = 0; i < size; i++) {
      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableRedIds[i], options);
      BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
          bitmap);
      drawables[i] = bitmapDrawable;
    }

    BitmapDrawable drawable = (BitmapDrawable) drawables[0];
    Bitmap drawableBitmap = drawable.getBitmap();
    LLogger.d("drawableBitmap:" + drawableBitmap.getByteCount());

    imageProgressView = new ImageProgressView(imageView, drawables);

    seekBar.setOnProcessChangeListener(new OnProcessChangeListener() {

      @Override
      public void onProgressChanged(int progress, boolean isPressedSlide) {
        LLogger.d("progress:" + progress + " pressed:" + isPressedSlide);
        imageProgressView.updateByProcessStep(progress, isPressedSlide);

      }

      @Override
      public void onProgressChangeStatus(boolean isStart) {
        LLogger.d("isChange:" + isStart);
        imageProgressView.updateChange(isStart);
      }

    });
  }


}
