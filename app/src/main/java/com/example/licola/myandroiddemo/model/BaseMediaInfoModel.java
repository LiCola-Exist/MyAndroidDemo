package com.example.licola.myandroiddemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LiCola
 * @date 2019-05-08
 */
public class BaseMediaInfoModel implements Parcelable {

  private final String path;
  private final int width;
  private final int height;
  private final String mimeType;

  public BaseMediaInfoModel(String path, int width, int height, String mimeType) {
    this.path = path;
    this.width = width;
    this.height = height;
    this.mimeType = mimeType;
  }

  public String getPath() {
    return path;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }


  public String getMimeType() {
    return mimeType;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.path);
    dest.writeInt(this.width);
    dest.writeInt(this.height);
    dest.writeString(this.mimeType);
  }

  protected BaseMediaInfoModel(Parcel in) {
    this.path = in.readString();
    this.width = in.readInt();
    this.height = in.readInt();
    this.mimeType = in.readString();
  }

  public static final Creator<BaseMediaInfoModel> CREATOR = new Creator<BaseMediaInfoModel>() {
    @Override
    public BaseMediaInfoModel createFromParcel(Parcel source) {
      return new BaseMediaInfoModel(source);
    }

    @Override
    public BaseMediaInfoModel[] newArray(int size) {
      return new BaseMediaInfoModel[size];
    }
  };
}
