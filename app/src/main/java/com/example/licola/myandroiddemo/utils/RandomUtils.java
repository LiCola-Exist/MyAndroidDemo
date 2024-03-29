package com.example.licola.myandroiddemo.utils;

import android.support.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 李可乐 on 2017/4/18.
 */

public class RandomUtils {

  /**
   * 得到数字组成的随机字符串
   */
  public static String getRandomNumberString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    if (length <= 1) {//一位长度
      return stringBuilder.append(ThreadLocalRandom.current().nextInt(10)).toString();
    }
    for (int i = 0; i < length; i++) {
      if (i == 0) {//防止出现首位为0的数字
        stringBuilder.append(ThreadLocalRandom.current().nextInt(1, 10));
      } else {
        stringBuilder.append(ThreadLocalRandom.current().nextInt(10));
      }
    }
    return stringBuilder.toString();
  }

  /**
   * 得到随机符号组成的字符串
   */
  public static String getRandomSymbolString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      stringBuilder.append((char) ThreadLocalRandom.current().nextInt(33, 128));
    }
    return stringBuilder.toString();
  }

  /**
   * 得到随机中文字符串
   */
  public static String getRandomChinese(int length) {
    char[] chars = new char[length];
    for (int i = 0; i < chars.length; i++) {
      chars[i] = getRandomChineseChar();
    }
    return new String(chars);
  }

  private static char getRandomChineseChar() {
    return (char) (0x4e00 + (int) (ThreadLocalRandom.current().nextDouble(1) * (0x9fa5 - 0x4e00
        + 1)));
  }


  /**
   * 得到随机中文简体字符串
   */
  public static String getRandomChineseSimple(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      stringBuilder.append(getRandomChineseCharSimple());
    }
    return stringBuilder.toString();
  }

  @Nullable
  private static String getRandomChineseCharSimple() {
    String str = null;
    // 定义高低位
    int highPos;
    int lowPos;
    highPos = (176 + Math.abs(ThreadLocalRandom.current().nextInt(39))); //获取高位值
    lowPos = (161 + Math.abs(ThreadLocalRandom.current().nextInt(93))); //获取低位值
    byte[] b = new byte[2];
    b[0] = (Integer.valueOf(highPos).byteValue());
    b[1] = (Integer.valueOf(lowPos).byteValue());
    try {
      str = new String(b, "GBk"); //转成中文
    } catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }
    return str;
  }


}
