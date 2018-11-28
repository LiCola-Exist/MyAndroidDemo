package com.example.licola.myandroiddemo.utils;

import okio.ByteString;

/**
 * @author LiCola
 * @date 2018/11/28
 */
public class StringUtils {

  public static String md5hex(String input) {
    if (input==null||input.isEmpty()) {
      return "";
    }
    return ByteString.encodeUtf8(input).md5().hex();
  }
}
