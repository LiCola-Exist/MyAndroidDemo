package com.example.licola.myandroiddemo.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.licola.myandroiddemo.R;

/**
 * Created by LiCola on 2017/10/25.
 */

public class TickView extends View {

  private Context mContext;

  //内圆画笔
  private Paint mPaintCircle;
  //外圆弧画笔
  private Paint mPaintRing;
  //钩的画笔
  private Paint mPaintTick;

  //整个View的圆外切矩形
  private RectF mRectF = new RectF();
  //记录打钩路径的三个点坐标
  private float[] mPoints = new float[8];

  //View中心坐标
  private int centerX;
  private int centerY;

  //动画计数器
  private int ringCounter = 0;
  private int circleCounter = 0;
  private float scaleCounter = 0;
  private int alphaCounter = 0;

  //状态值 是否选中
  private boolean isChecked = false;

  private int unCheckBaseColor;
  private int checkBaseColor;
  private int checkTickColor;
  private int defaultRadius;
  private float circleRadius;
  private float scaleRingWith;
  //默认圆 占据View宽高的的比例
  private final float circleRadiusRate = 0.7f;
  //膨胀圆 的比例
  private final float scaleCounterRate = 1 - circleRadiusRate;

  private TickRateEnum mTickRateEnum;

  private OnCheckedChangeListener mOnCheckedChangeListener;

  public TickView(Context context) {
    this(context, null);
  }

  public TickView(Context context,
      @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    defaultRadius = dp2px(mContext,24);//默认圆半径
    initAttrs(attrs);
    init();
    setUpEvent();
  }


  private void initAttrs(AttributeSet attrs) {
    TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TickView);
    Resources resources = getResources();
    unCheckBaseColor = typedArray
        .getColor(R.styleable.TickView_uncheck_base_color, resources.getColor(R.color.red_normal));
    checkBaseColor = typedArray
        .getColor(R.styleable.TickView_check_base_color, resources.getColor(R.color.blue_normal));
    checkTickColor = typedArray
        .getColor(R.styleable.TickView_check_tick_color, resources.getColor(R.color.white_normal));
    int rateMode = typedArray.getInt(R.styleable.TickView_rate, 1);
    mTickRateEnum = TickRateEnum.getRateEnum(rateMode);
    typedArray.recycle();//记得回收

  }

  private void init() {
    if (mPaintRing == null) {
      mPaintRing = new Paint(Paint.ANTI_ALIAS_FLAG);//构造抗锯齿 画笔
    }
    //设置 圆环画笔
    mPaintRing.setStyle(Style.STROKE);//画线
    mPaintRing.setStrokeWidth(dp2px(mContext, 2.5f));//线条宽度 单位像素
    mPaintRing.setColor(isChecked ? checkBaseColor : unCheckBaseColor);
    mPaintRing.setStrokeCap(Cap.ROUND);//设置 画笔线头 形状

    //设置内圆画笔
    if (mPaintCircle == null) {
      mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    mPaintCircle.setColor(checkBaseColor);
    mPaintCircle.setStrokeWidth(dp2px(mContext, 1));

    //设置钩的画笔
    if (mPaintTick == null) {
      mPaintTick = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    mPaintTick.setColor(isChecked ? checkTickColor : unCheckBaseColor);
    mPaintTick.setStyle(Paint.Style.STROKE);
    mPaintTick.setStrokeCap(Paint.Cap.ROUND);
    mPaintTick.setStrokeWidth(dp2px(mContext, 2.5f));
  }

  private void setUpEvent() {
    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        isChecked = !isChecked;
        reset();
        if (mOnCheckedChangeListener != null) {
          mOnCheckedChangeListener.onCheckedChanged((TickView) v, isChecked);
        }
      }
    });
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int defaultSize = (int) (defaultRadius * 1.5 * 2);//默认大小 1.5倍默认半径*2
    int width = getMySize(defaultSize, widthMeasureSpec);
    int height = getMySize(defaultSize, heightMeasureSpec);

    height = width = Math.max(width, height);

    setMeasuredDimension(width, height);//设置 计算得到的 实际宽高

    centerX = getMeasuredWidth() >> 1;
    centerY = getMeasuredHeight() >> 1;

    circleRadius = centerX * circleRadiusRate;//计算得到默认圆 半径
    scaleRingWith = centerX * scaleCounterRate;//计算得到 圆弧的膨胀宽度

    mRectF.set(centerX - circleRadius, centerY - circleRadius, centerX + circleRadius,
        centerY + circleRadius);//设置 圆的外切矩形 坐标方式设置 坐标起点在左上角

    //得到比例相关的 钩 数值
    float tickRadius = centerX * scaleCounterRate;
    float tickRadiusOffset = dp2px(mContext, 4);

    //设置打钩的几个点坐标
    mPoints[0] = centerX - tickRadius + tickRadiusOffset;
    mPoints[1] = (float) centerY;
    mPoints[2] = centerX - tickRadius / 2 + tickRadiusOffset;
    mPoints[3] = centerY + tickRadius / 2;
    mPoints[4] = centerX - tickRadius / 2 + tickRadiusOffset;
    mPoints[5] = centerY + tickRadius / 2;
    mPoints[6] = centerX + tickRadius * 2 / 4 + tickRadiusOffset;
    mPoints[7] = centerY - tickRadius * 2 / 4;

  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (!isChecked) {
      //非选中 直接绘制 圆环 和钩
      canvas.drawCircle(centerX, centerY, circleRadius, mPaintRing);
      canvas.drawLines(mPoints, mPaintTick);
      return;
    }

    //圆弧
    ringCounter += mTickRateEnum.getRingCounterUnit();//动态圆弧进度 递增
    ringCounter = ringCounter >= 360 ? 360 : ringCounter;//修正
    if (ringCounter <= 360) {//圆弧未连接 完成圆 才绘制
      canvas.drawArc(mRectF, 90, ringCounter, false, mPaintRing);//绘制
    }

    if (ringCounter == 360) {//圆弧完成
      //画圆
      mPaintCircle.setColor(checkBaseColor);
      canvas.drawCircle(centerX, centerY, circleRadius, mPaintCircle);
      //绘制 白色的圆
      mPaintCircle.setColor(checkTickColor);
      int circleCounterUnit = mTickRateEnum.getCircleCounterUnit();
      circleCounter += circleCounterUnit;//动态圆圈半径 递增 覆盖半径 递减
      canvas.drawCircle(centerX, centerY, circleRadius - circleCounter,
          mPaintCircle);//绘制 白色的内圆 覆盖在外圆上

      if (circleCounter >= circleRadius + circleCounterUnit) {//修正一个单位
        alphaCounter += 25;
        alphaCounter = alphaCounter >= 255 ? 255 : alphaCounter;
        mPaintTick.setAlpha(alphaCounter);//设置 透明度 渐进的显示 钩
        canvas.drawLines(mPoints, mPaintTick);//画钩

        scaleCounter += mTickRateEnum.getScaleCounterUnit();//缩放计数
        scaleCounter = scaleCounter >= 180 ? 180 : scaleCounter;

        //画动态边宽圆弧 （画圆 会覆盖 ）且 画笔宽度增长 左右各半
        double dyWith = scaleRingWith * Math.sin(Math.toRadians(scaleCounter)) * 2;
        mPaintRing.setStrokeWidth((float) dyWith);
        canvas.drawArc(mRectF, 90, 360, false, mPaintRing);
      }
    }

    if (scaleCounter < 180) {
      postInvalidate();
    }
  }

  /**
   *
   * @param defaultSize
   * @param measureSpec
   * @return
   */
  private int getMySize(int defaultSize, int measureSpec) {

    int sizeResult = defaultSize;

    int mode = MeasureSpec.getMode(measureSpec);
    int size = MeasureSpec.getSize(measureSpec);

    switch (mode) {
      case MeasureSpec.UNSPECIFIED:
      case MeasureSpec.AT_MOST:
        //当 wrap时 为AT_MOST 需要指定默认宽度
        sizeResult = defaultSize;
        break;
      case MeasureSpec.EXACTLY:
        sizeResult = size;
        break;
    }

    return sizeResult;
  }

  //重置 按钮形态
  private void reset() {
    init();

    ringCounter = 0;
    circleCounter = 0;
    scaleCounter = 0;
    alphaCounter = 0;

    invalidate();
  }


  public void setChecked(boolean checked) {
    if (this.isChecked != checked) {
      isChecked = checked;
      reset();
    }
  }

  public interface OnCheckedChangeListener {

    void onCheckedChanged(TickView tickView, boolean isCheck);
  }

  public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
    this.mOnCheckedChangeListener = listener;
  }

  private static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  private static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}
