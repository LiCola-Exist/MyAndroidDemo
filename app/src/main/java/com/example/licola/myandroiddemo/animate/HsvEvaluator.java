package com.example.licola.myandroiddemo.animate;

import android.animation.TypeEvaluator;
import android.graphics.Color;

/**
 * Created by LiCola on 2017/9/12.
 */

public class HsvEvaluator implements TypeEvaluator<Integer> {

  float[] startHsv=new float[3];
  float[] endHsv=new float[3];
  float[] outrtHsv=new float[3];
  @Override public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
    //把ARGB 转换成 HSV
    Color.colorToHSV(startValue,startHsv);
    Color.colorToHSV(endValue,endHsv);




    return null;
  }
}
