package com.example.licola.myandroiddemo.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by LiCola on 2017/7/18.
 */

public class BitmapUtils {

  public static File saveBitmapToFile(Bitmap bitmap,String fileDirPath){
    OutputStream outputStream = null;
    String fileName = String.format(Locale.CHINA, "img_%d.jpg",
        System.currentTimeMillis());
    File fileDir = new File(fileDirPath);
    if (!fileDir.exists()) {
      fileDir.mkdirs();
    }
    File imageFile = new File(fileDirPath, fileName);
    try {
      outputStream = new FileOutputStream(imageFile);
      bitmap.compress(CompressFormat.JPEG, 100, outputStream);
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return imageFile;
  }

  public static Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int reqWidth,
      int reqHeight) {
    final BitmapFactory.Options options = new Options();
    options.inJustDecodeBounds = true;//配置参数
    BitmapFactory.decodeResource(resources, resId, options);//传入参数 得到图片信息
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(resources, resId, options);
  }

  private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      final int halfHeight = height >> 1;
      final int halfWidth = width >> 1;
      while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
        inSampleSize*=2;
      }
    }
    return inSampleSize;
  }
}
