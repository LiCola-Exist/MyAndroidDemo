package com.example.licola.myandroiddemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LiCola
 * @date 2019-05-08
 */
public class VideoMediaInfoModel extends BaseMediaInfoModel implements Parcelable {

  private final int duration;
  private final int bitrate;
  private final int rotation;

  public VideoMediaInfoModel(String path, int duration, int width, int height, int bitrate,
      int rotation,
      String mimeType) {
    super(path, width, height,mimeType);
    this.duration = duration;
    this.bitrate = bitrate;
    this.rotation = rotation;
  }

  public int getDuration() {
    return duration;
  }

  public int getBitrate() {
    return bitrate;
  }

  public int getRotation() {
    return rotation;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(this.duration);
    dest.writeInt(this.bitrate);
    dest.writeInt(this.rotation);
  }

  protected VideoMediaInfoModel(Parcel in) {
    super(in);
    this.duration = in.readInt();
    this.bitrate = in.readInt();
    this.rotation = in.readInt();
  }

  public static final Creator<VideoMediaInfoModel> CREATOR = new Creator<VideoMediaInfoModel>() {
    @Override
    public VideoMediaInfoModel createFromParcel(Parcel source) {
      return new VideoMediaInfoModel(source);
    }

    @Override
    public VideoMediaInfoModel[] newArray(int size) {
      return new VideoMediaInfoModel[size];
    }
  };
}
