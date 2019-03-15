package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.example.licola.myandroiddemo.view.widget.CanvasDemoView;
import com.example.licola.myandroiddemo.view.widget.ShaderDemoView;
import com.example.licola.myandroiddemo.view.widget.TextCaptionView;
import com.licola.llogger.LLogger;

/**
 * Created by 李可乐 on 2016/12/9 0009.
 */

public class ViewDrawFragment extends BaseFragment {

  private static final String ARG_SECTION_KEY = "section_key";

  public ViewDrawFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section number.
   */
  public static ViewDrawFragment newInstance(String key) {
    ViewDrawFragment fragment = new ViewDrawFragment();
    Bundle args = new Bundle();
    args.putString(ARG_SECTION_KEY, key);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
//    AsyncLayoutInflater asyncLayoutInflater=new AsyncLayoutInflater(getContext());
//    asyncLayoutInflater.inflate(R.layout.fragment_image_view, container,
//        new OnInflateFinishedListener() {
//          @Override
//          public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
//
//          }
//        });

    final View rootView = inflater.inflate(R.layout.fragment_view, container, false);

    final LinearLayout layoutGroup = rootView.findViewById(R.id.layout_group);

    final int screenWidth = PixelUtils.getScreenWidth(getContext());

    handleTouch((NestedScrollView) rootView, rootView.findViewById(R.id.layout_touche));

    addTextCaptionView(rootView.findViewById(R.id.layout_draw_group), screenWidth);

    addCanvasView(layoutGroup, screenWidth);
    addShaderView(layoutGroup, screenWidth);

    bindAutoSize(rootView.findViewById(R.id.tv_auto_size));

    return rootView;
  }

  private void bindAutoSize(TextView tvAutoSize) {
    tvAutoSize.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ViewGroup.LayoutParams layoutParams = tvAutoSize.getLayoutParams();
        layoutParams.height -= 50;
        layoutParams.width -= 50;
        tvAutoSize.setLayoutParams(layoutParams);
      }
    });
  }

  private void addTextCaptionView(final ViewGroup layoutGroup, final int maxWidth) {

    final int fixHeight = 400;

    final FrameLayout frameGroup = new FrameLayout(getContext());
    frameGroup.setBackgroundResource(R.color.blue_normal_A54);
    layoutGroup.addView(frameGroup, new LayoutParams(maxWidth, maxWidth));

    final TextCaptionView textCaptionView = new TextCaptionView(getContext());

//    Drawable topDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_album);
//    textCaptionView.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);

    textCaptionView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80);
    textCaptionView.setMaxWidth(80);
    textCaptionView.setText("Caption字幕123");
    textCaptionView.setGravity(Gravity.CENTER);
//    textCaptionView.setVisibility(View.INVISIBLE);
    textCaptionView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font.ttf"));
    textCaptionView.setBackgroundResource(R.color.black_normal_A12);

    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER;

    frameGroup.addView(textCaptionView, layoutParams);

    textCaptionView.post(new Runnable() {
      @Override
      public void run() {
        int copyWidth = frameGroup.getWidth();
        int copyHeight = frameGroup.getHeight();
        Bitmap textBmp = Bitmap.createBitmap(copyWidth, copyHeight, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(textBmp);
        frameGroup.draw(canvas);

        ImageView copyImageView = new ImageView(getContext());
        copyImageView.setBackgroundResource(R.color.blue_normal_A12);
        copyImageView.setImageBitmap(textBmp);

        LayoutParams copyLayoutParams = new LayoutParams(copyWidth, copyHeight);
        copyLayoutParams.gravity = Gravity.CENTER;

        layoutGroup.addView(copyImageView, copyLayoutParams);
      }
    });


  }

  private void addShaderView(final LinearLayout layoutGroup, int maxWidth) {
    ShaderDemoView shaderDemoView = new ShaderDemoView(getContext());

    shaderDemoView.setBackgroundResource(R.color.black_normal_A87);

    layoutGroup.addView(shaderDemoView,
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxWidth));
  }

  private void addCanvasView(LinearLayout layoutGroup, int maxWidth) {
    final View canvasView = new CanvasDemoView(getContext());

    canvasView.setBackgroundResource(R.color.black_normal_A54);

    layoutGroup.addView(canvasView,
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxWidth));
  }

  private void handleTouch(final NestedScrollView rootView, ViewGroup touch) {
    touch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        LLogger.d("响应了 点击事件");
      }
    });

    touch.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        //长按复制出 ScrollView的内容 并显示
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(Color.GRAY);
        Bitmap screenShotBitmap = getScrollViewBitmap(rootView);
        imageView.setImageBitmap(screenShotBitmap);

        touch.addView(imageView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        return true;
      }
    });

//    touch.setOnTouchListener(new OnTouchListener() {
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        LLogger.d("ViewGroup Touch 拦截点击事件");
//
//        try {
//          Thread.sleep(100000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//
//        return true;
//      }
//    });
  }

  public static Bitmap getScrollViewBitmap(NestedScrollView scrollView) {
    int height = 0;
    Bitmap bitmap;
    for (int i = 0; i < scrollView.getChildCount(); i++) {
      height += scrollView.getChildAt(i).getHeight();
    }
    bitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Config.ARGB_8888);
    final Canvas canvas = new Canvas(bitmap);
    //用view绘制到canvas画布上
    scrollView.draw(canvas);
    //返回有内容的bitmap
    return bitmap;
  }

  private static class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context) {
      super(context);
    }

    public MyTextView(Context context,
        @Nullable AttributeSet attrs) {
      super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
    }
  }
}
