package com.example.licola.myandroiddemo.utils;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import com.example.licola.myandroiddemo.model.ImageMediaInfoModel;
import java.io.IOException;

/**
 * @author LiCola
 * @date 2019-05-08
 */
public class ImageMediaUtils {

  public static ImageMediaInfoModel getImageInfo(String path) {

    Options opts = new Options();
    opts.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, opts);

    int outWidth;
    int outHeight;

    if (needRotate(path)) {
      outWidth = opts.outHeight;
      outHeight = opts.outWidth;
    } else {
      outWidth = opts.outWidth;
      outHeight = opts.outHeight;
    }

    return new ImageMediaInfoModel(path, outWidth, outHeight, opts.outMimeType);
  }

  /**
   * 特殊的本地拍摄的图片都是横屏的（大概值底层相机组件是横屏的），所谓竖屏其实是横屏+90/270度角。
   * 这个角度信息写在文件信息里Exchangeable image file format里，
   * @param path
   * @return
   */
  public static boolean needRotate(String path) {

    int orientation = ExifInterface.ORIENTATION_NORMAL;

    try {
      ExifInterface exifInterface = new ExifInterface(path);

      orientation = exifInterface
          .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    } catch (IOException e) {
    }

    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90:
      case ExifInterface.ORIENTATION_ROTATE_270:
        return true;
      case ExifInterface.ORIENTATION_ROTATE_180:
      case ExifInterface.ORIENTATION_NORMAL:
      default:
        return false;
    }

  }
}
