package com.example.licola.myandroiddemo;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.licola.llogger.LLogger;
import com.example.licola.myandroiddemo.view.TouchImageView;


/**
 *
 */
public class ImageViewFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  public ImageViewFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ImageViewFragment.
   */
  // TODO: Rename and change types and number of parameters
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

    TouchImageView imgTouch = childView.findViewById(R.id.img_touch);
    imgTouch.setImageDrawable(getResources().getDrawable(R.drawable.p18684001092782349));

    final ImageView imageView=childView.findViewById(R.id.img_load);
    Glide.with(getActivity()).load(R.drawable.a01).into(imageView);
    imageView.postDelayed(new Runnable() {
      @Override
      public void run() {
        Drawable drawable = imageView.getDrawable();
      }
    },1000);

    ImageView imgBitmap=childView.findViewById(R.id.img_bitmap);
    Options options=new Options();
    options.inSampleSize=2;
    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.a01,options);
    imgBitmap.setImageBitmap(bitmap);
    LLogger.d("bitmap byte size:"+bitmap.getByteCount());
    LLogger.d("bitmap width:"+bitmap.getWidth()+" height:"+bitmap.getHeight()+" config："+bitmap.getConfig());

    return childView;
  }

}
