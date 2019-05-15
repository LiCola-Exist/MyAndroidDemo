package com.example.licola.myandroiddemo.utils;

import android.media.MediaMetadataRetriever;
import com.example.licola.myandroiddemo.model.VideoMediaInfoModel;

/**
 * @author LiCola
 * @date 2019-05-07
 */
public class VideoMediaUtils {

  public static VideoMediaInfoModel getVideoInfo(String path) {

    if (path == null || path.isEmpty()) {
      return null;
    }

    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();

    metadataRetriever.setDataSource(path);

    int height = Integer.parseInt(
        metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

    int width = Integer.parseInt(
        metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

    int duration = Integer
        .parseInt(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

    int bitrate = Integer
        .parseInt(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

    int rotation = Integer.parseInt(
        metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));

    String mimeType = metadataRetriever
        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);

    metadataRetriever.release();

    return new VideoMediaInfoModel(path, duration, width, height, bitrate, rotation, mimeType);
  }
}
