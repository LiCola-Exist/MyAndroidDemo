package com.example.licola.myandroiddemo.frag;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.io.FileUtils;
import com.example.licola.myandroiddemo.utils.ImageMediaUtils;
import com.example.licola.myandroiddemo.view.widget.TouchImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.licola.llogger.LLogger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 *
 */
public class ImageFragment extends BaseFragment {

  private static final Uri FRESCO_LOGO = Uri
      .parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");

  private static final Uri GLIDE_LOGO = Uri
      .parse("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg");

  /**
   *
   */
  private static final String ORIENTATION_DEGREES_IMAGE = "rotation_image.jpg";

  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;

  @BindView(R.id.img_load_fresco)
  SimpleDraweeView simpleDraweeView;
  @BindView(R.id.img_load_glide)
  ImageView imageGlide;
  @BindView(R.id.img_load_bitmap)
  ImageView imageLoadBitmap;
  @BindView(R.id.img_bitmap_compress)
  ImageView imageCompressBitmap;
  @BindView(R.id.img_bitmap_compress_scale)
  ImageView imageCompressBitmapScale;

  @BindView(R.id.img_load_touch)
  TouchImageView touchImageView;

  @BindView(R.id.img_load_drawable_first)
  ImageView imageLoadBitmapFirst;
  @BindView(R.id.img_load_drawable_second)
  ImageView imageLoadBitmapSecond;
  @BindView(R.id.img_load_drawable_third)
  ImageView imageLoadBitmapThird;

  File fileLocal;

  public ImageFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_image_view;
  }


  public static ImageFragment newInstance(String param1) {
    ImageFragment fragment = new ImageFragment();
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
    fileLocal = loadImageToLocal(ORIENTATION_DEGREES_IMAGE);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = super.onCreateView(inflater, container, savedInstanceState);

    loadByFresco(FRESCO_LOGO);
    loadByGlide(GLIDE_LOGO);
    loadByOriginal();
    loadByOriginalWithOptions(fileLocal.getAbsolutePath());
    loadByDrawable();

    return rootView;
  }

  private void compressImageFile(File imageLocal) {
    Luban.with(getContext())
        .load(imageLocal)
        .setCompressListener(new OnCompressListener() {
          @Override
          public void onStart() {
            LLogger.d();
          }

          @Override
          public void onSuccess(File file) {
            LLogger.d(file, file.length(), Thread.currentThread());
          }

          @Override
          public void onError(Throwable e) {
            LLogger.e(e);
          }
        }).launch();

  }


  private void loadByDrawable() {

    final StateListDrawable drawable = (StateListDrawable) ContextCompat
        .getDrawable(getContext(), R.drawable.selector_app_enable);

    imageLoadBitmapFirst.setImageDrawable(drawable.mutate());
    imageLoadBitmapSecond.setImageDrawable(drawable);

    imageLoadBitmapFirst.setOnClickListener(v -> v.setActivated(!v.isActivated()));

    imageLoadBitmapThird.setImageURI(
        Uri.parse("android.resource://" + getActivity().getPackageName() + "//"
            + R.drawable.ic_action_app_white));


  }

  private void loadByFresco(Uri uri) {
    simpleDraweeView.setImageURI(uri);
  }

  private void loadByGlide(Uri uri) {
    Glide.with(getActivity())
        .load(uri)
        .into(new ImageViewTarget<Drawable>(imageGlide) {
          @Override
          protected void setResource(@Nullable Drawable resource) {
            LLogger.d(resource);
            imageGlide.setImageDrawable(resource);
          }
        });

    imageGlide.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        RequestOptions options = new RequestOptions().circleCrop();
        Glide.with(getActivity())
            .load(uri)
            .apply(options)
            .into(imageGlide);
      }
    });
  }

  private void loadByOriginalWithOptions(String pathName) {

    Options options = new Options();

    options.inJustDecodeBounds = true;//只获取图片边界信息
    BitmapFactory.decodeFile(pathName, options);

    int outHeight = options.outHeight;
    int outWidth = options.outWidth;
    String outMimeType = options.outMimeType;
    LLogger.d(outHeight, outWidth, outMimeType);

    boolean needRotate = ImageMediaUtils.needRotate(pathName);

    options.inJustDecodeBounds = false;
    options.inSampleSize = 2;//缩放设置 缩小2倍
    //从drawable中加载 能够取出默认的图片比例：系统dpi/drawable的dpi
    Bitmap bitmapDecode = BitmapFactory.decodeFile(pathName, options);

    LLogger.d("bitmap byte size:" + bitmapDecode.getByteCount(),
        bitmapDecode.getHeight(),
        bitmapDecode.getWidth(),
        bitmapDecode.getConfig()
    );

    Bitmap resultBitmap;

    if (needRotate) {
      Matrix matrix = new Matrix();
      matrix.setRotate(90);
      resultBitmap = Bitmap
          .createBitmap(bitmapDecode, 0, 0, bitmapDecode.getWidth(), bitmapDecode.getHeight(),
              matrix, false);
    } else {
      resultBitmap = bitmapDecode;
    }

    imageLoadBitmap.setScaleType(ScaleType.CENTER_INSIDE);
    imageLoadBitmap.setImageBitmap(resultBitmap);

    imageCompressBitmap.setImageBitmap(compressQuality(bitmapDecode));
    imageCompressBitmapScale.setImageBitmap(compressScale(bitmapDecode));

  }

  private Bitmap compressQuality(Bitmap bitmapInput) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmapInput.compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
    byte[] bytesCompress = byteArrayOutputStream.toByteArray();
    Bitmap bitmapCompress = BitmapFactory.decodeByteArray(bytesCompress, 0, bytesCompress.length);

    LLogger.d("compress quality:" + bitmapCompress.getByteCount(),
        bitmapCompress.getHeight(),
        bitmapCompress.getWidth(),
        bitmapCompress.getConfig()
    );
    return bitmapCompress;
  }

  /**
   * 相比inSampleSize采样率2的倍数参数限制，矩阵缩放支持浮点输入，一样也维持宽高比例
   */
  private Bitmap compressScale(Bitmap bitmapInput) {
    Matrix matrix = new Matrix();
    matrix.setScale(0.3f, 0.3f);
    Bitmap bitmapCompress = Bitmap
        .createBitmap(bitmapInput, 0, 0, bitmapInput.getWidth(), bitmapInput.getHeight(),
            matrix, true);

    LLogger.d("compress scale:" + bitmapCompress.getByteCount(),
        bitmapCompress.getHeight(),
        bitmapCompress.getWidth(),
        bitmapCompress.getConfig()
    );

    return bitmapCompress;
  }

  private void loadByOriginal() {
    touchImageView.setImageDrawable(getResources().getDrawable(R.drawable.image_square_small));
  }

  @OnClick(R.id.btn_image_compress)
  public void btnImageCompressClick(View view) {
    compressImageFile(fileLocal);
  }

  private File loadImageToLocal(String fileName) {
    File filesDir = mContext.getFilesDir();
    File outTargetFile = new File(filesDir, fileName);

    if (!outTargetFile.exists()) {
      InputStream inputStream = null;
      try {
        inputStream = mContext.getAssets().open(fileName);
      } catch (IOException e) {
        e.printStackTrace();
      }
      FileUtils.writeFile(outTargetFile, inputStream);
      LLogger.d("写入本地成功");
    }

    LLogger.d(outTargetFile, outTargetFile.length());

    return outTargetFile;
  }


}
