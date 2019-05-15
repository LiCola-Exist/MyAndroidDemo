package com.example.licola.myandroiddemo.model;

import android.os.Parcelable;

/**
 * @author LiCola
 * @date 2019-05-08
 */
public class ImageMediaInfoModel extends BaseMediaInfoModel implements Parcelable {

  public ImageMediaInfoModel(String path, int width, int height, String mimeType) {
    super(path, width, height, mimeType);
  }
}
