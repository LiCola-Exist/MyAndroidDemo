package com.example.licola.myandroiddemo.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window.OnFrameMetricsAvailableListener;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;

public class LayoutActivity extends AppCompatActivity {

  private static final String LAYOUT_TYPE = "layout_type";


  public static void start(Context context, @LayoutRes int layoutId) {
    Intent intent = new Intent(context, LayoutActivity.class);
    intent.putExtra(LAYOUT_TYPE, layoutId);
    context.startActivity(intent);
  }

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    int layoutId = intent.getIntExtra(LAYOUT_TYPE, 0);
    setContentView(layoutId);
    if (layoutId == R.layout.activity_linear) {
      LLogger.d("线性");
    } else if (layoutId == R.layout.activity_relative) {
      LLogger.d("相对");
    }

    HandlerThread handlerThread = new HandlerThread("frame");
    handlerThread.start();


    getWindow().addOnFrameMetricsAvailableListener(new OnFrameMetricsAvailableListener() {
      @Override
      public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics,
          int dropCountSinceLastInvocation) {
        LLogger.d(
            convert(frameMetrics.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)),
            convert(frameMetrics.getMetric(FrameMetrics.DRAW_DURATION)),
            convert(frameMetrics.getMetric(FrameMetrics.TOTAL_DURATION))
        );
      }
    }, new Handler(handlerThread.getLooper()));
  }

  public static final String convert(long nanoseconds) {
    return (nanoseconds / 1000000.0) + "ms";
  }


}
