package com.example.licola.myandroiddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.licola.myandroiddemo.view.TouchImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.licola.llogger.LLogger;


/**
 *
 */
public class ImageViewFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  public ImageViewFragment() {
    // Required empty public constructor
  }


  public static ImageViewFragment newInstance(String param1) {
    ImageViewFragment fragment = new ImageViewFragment();
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
    View childView = inflater.inflate(R.layout.fragment_image_view, container, false);

    loadByFresco(childView);
    loadByGlide(childView);
    loadByOriginalWithOptions(childView);
    loadByDrawable(childView);
    loadByOriginal(childView);

    return childView;
  }

  private void loadByDrawable(View childView) {
    final ImageView imgFirst = childView.findViewById(R.id.img_load_drawable_first);
    final ImageView imgSecond = childView.findViewById(R.id.img_load_drawable_second);

    final StateListDrawable drawable = (StateListDrawable) ContextCompat
        .getDrawable(getContext(), R.drawable.selector_app_enable);

    imgFirst.setImageDrawable(drawable.mutate());
    imgSecond.setImageDrawable(drawable);

    imgFirst.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        v.setActivated(!v.isActivated());
      }
    });

  }

  private void loadByFresco(View childView) {
    Uri uri = Uri
        .parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
    SimpleDraweeView imageView = childView.findViewById(R.id.img_load_fresco);
    imageView.setImageURI(uri);
  }

  private void loadByGlide(View childView) {
    final ImageView imageView = childView.findViewById(R.id.img_load_glide);
    Glide.with(getActivity())
        .load("https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png")
        .into(imageView);
//    imageView.postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        Drawable drawable = imageView.getDrawable();
//      }
//    },1000);
  }

  private void loadByOriginal(View childView) {
    TouchImageView imgTouch = childView.findViewById(R.id.img_load_touch);
    imgTouch.setImageDrawable(getResources().getDrawable(R.drawable.p18684001092782349));
  }

  private void loadByOriginalWithOptions(View childView) {
    ImageView imgBitmap = childView.findViewById(R.id.img_load_bitmap);
    Options options = new Options();
    options.inSampleSize = 2;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a01, options);
    imgBitmap.setImageBitmap(bitmap);
    LLogger.d("bitmap byte size:" + bitmap.getByteCount());
    LLogger.d("bitmap width:" + bitmap.getWidth() + " height:" + bitmap.getHeight() + " config："
        + bitmap.getConfig());
  }


}
