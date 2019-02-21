package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.utils.BitmapUtils;

/**
 * Created by LiCola on 2017/11/1.
 */

public class CanvasDemoView extends View {

  private final Matrix matrix;
  Paint paint;
  Bitmap bitmap;

  public CanvasDemoView(Context context) {
    super(context);
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.BLUE);
    bitmap =
        BitmapUtils.decodeSampleBitmapFromResource(getResources(), R.drawable.p18684001092782349,
            400, 400);
    this.matrix = new Matrix();
  }

  @Override protected void onDraw(Canvas canvas) {
    canvas.save();

    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    int halfWidth = width >> 1;
    int halfHeight = height >> 1;
    int quarterWidth = width >> 2;
    int quarterHeight = height >> 2;
    int left = 0;
    int right = width;
    int top = 0;
    int bottom = height;

    canvas.drawRect(0, 0, width, height, paint);
    canvas.save();
    //canvas.clipRect(halfWidth-quarterWidth,halfHeight-quarterHeight,halfWidth+quarterWidth,halfHeight+quarterHeight);
    //canvas.translate(200,200);//位移
    //canvas.rotate(a45,halfWidth,halfHeight);//旋转 单位角度 默认从左上角开始 可以指定中心
    //canvas.scale(0.5f,0.5f,halfWidth,halfHeight);//缩放 sx/sy单位浮点数 默认从左上角开始 可以指定中心
    //canvas.skew(0.5f,0.5f);//倾斜

    matrix.reset();
    //matrix.postRotate(a45,halfWidth,halfHeight);//矩阵的旋转操作 操作方式同对Canvas操作类似 结果一样
    //matrix.postScale(0.5f,0.5f);//矩阵的缩放操作 同上
    float[] pointSrc = { left, top, right, top, left, bottom, right, bottom };
    float[] pointDst = {
        left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20,
        bottom + 60
    };
    matrix.setPolyToPoly(pointSrc, 0, pointDst, 0, 4);
    canvas.concat(matrix);

    canvas.drawBitmap(bitmap, 0, 0, paint);
    canvas.restore();
  }
}

