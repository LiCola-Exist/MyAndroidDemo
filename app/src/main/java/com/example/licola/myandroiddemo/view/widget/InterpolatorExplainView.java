package com.example.licola.myandroiddemo.view.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by LiCola on 2017/11/1.
 */

public class InterpolatorExplainView extends View {

  Paint paint;
  Paint paintText;
  Paint paintBaseLine;

  int paintWidth = 10;

  Path pathLineX;
  Path pathLineY;
  Path pathXY;

  String interpolatorName;

  public InterpolatorExplainView(Context context) {
    super(context);

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.BLUE);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStyle(Style.STROKE);
    paint.setStrokeWidth(paintWidth);

    paintBaseLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    paintBaseLine.setColor(Color.GRAY);
    paintBaseLine.setStrokeJoin(Paint.Join.ROUND);
    paintBaseLine.setStrokeCap(Paint.Cap.ROUND);
    paintBaseLine.setStyle(Style.STROKE);
    paintBaseLine.setStrokeWidth(paintWidth);

    paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    paintText.setColor(Color.BLUE);
    paintText.setTextSize(50);

    pathLineX = new Path();
    pathLineY = new Path();
    pathXY = new Path();
  }

  public TimeInterpolator getInterpolatorByFlag(int flag) {
    switch (flag) {
      case 0:
        return new LinearInterpolator();
      case 1:
        return new AccelerateInterpolator();
      case 2:
        return new DecelerateInterpolator();
      case 3:
        return new AccelerateDecelerateInterpolator();
      case 4:
        return new AnticipateInterpolator();
      case 5:
        return new OvershootInterpolator();
      case 6:
        return new AnticipateOvershootInterpolator();
      case 7:
        return new BounceInterpolator();
      case 8:
        return new FastOutLinearInInterpolator();
      case 9:
        return new FastOutSlowInInterpolator();
      case 10:
        return new LinearOutSlowInInterpolator();
    }
    return null;
  }

  public void startPathAnimator(TimeInterpolator value) {
    final int width = 800;
    final int height = 500;

    final int offestX = 200;
    final int offestY = 200;

    int baseX = offestX;
    int baseY = height + offestY;
    pathLineX.moveTo(offestX, baseY);
    pathLineX.lineTo(width + offestX, baseY);
    pathLineY.moveTo(offestX, baseY);
    pathLineY.lineTo(baseX, height + offestY);

    pathXY.reset();
    pathXY.moveTo(baseX, baseY);

    final int duration = 1000;

    String simpleName = value.getClass().getSimpleName();
    this.interpolatorName = simpleName;

    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
    valueAnimator.setInterpolator(value);
    valueAnimator.setDuration(duration);
    valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float widthRate = animation.getCurrentPlayTime() / (duration * 1.0f);
        float resultX = widthRate * width + offestX;

        float data = (float) animation.getAnimatedValue();
        float resultY = (1 - data) * height + offestY;

        pathXY.lineTo(resultX, resultY);
        invalidate();
      }
    });
    valueAnimator.start();
  }

  @Override
  protected void onDraw(Canvas canvas) {

    canvas.drawPath(pathLineX, paintBaseLine);
    canvas.drawPath(pathLineY, paintBaseLine);
    canvas.drawPath(pathXY, paint);

    if (interpolatorName != null) {
      canvas.drawText(interpolatorName, 200, 200, paintText);
    }

  }
}
