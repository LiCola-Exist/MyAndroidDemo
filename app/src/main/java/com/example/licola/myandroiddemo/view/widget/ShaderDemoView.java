package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.view.View;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.BitmapUtils;

/**
 * Created by LiCola on 2017/11/1.
 */

public class ShaderDemoView extends View {
  Shader shaderLinear;
  Shader shaderRadial;
  Shader shaderSweep;
  Shader shaderBitmap;
  Paint paint;

  public ShaderDemoView(Context context) {
    super(context);
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    shaderLinear = new LinearGradient(0, 0, 200, 200, Color.parseColor("#E91E63"),
        Color.parseColor("#2196F3"), TileMode.CLAMP);
    shaderRadial = new RadialGradient(600, 200, 200, Color.parseColor("#E91E63"),
        Color.parseColor("#2196F3"), TileMode.CLAMP);
    shaderSweep =
        new SweepGradient(1000, 200, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"));

    Bitmap bitmap =
        BitmapUtils.decodeSampleBitmapFromResource(getResources(), R.drawable.p2415568603, 400,
            400);

    shaderBitmap = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
  }

  @Override protected void onDraw(Canvas canvas) {
    paint.setShader(shaderLinear);
    canvas.drawCircle(200, 200, 200, paint);

    //paint.setShader(shaderRadial);
    //canvas.drawCircle(600, 200, 200, paint);
    //
    //paint.setShader(shaderSweep);
    //canvas.drawCircle(1000, 200, 200, paint);

    paint.setShader(shaderBitmap);
    canvas.drawCircle(600, 200, 200, paint);

    paint.reset();
    paint.setTextSize(50);
    paint.setShadowLayer(10, 0, 0, Color.RED);
    canvas.drawText("Hello View !", 1000, 200, paint);
  }
}
