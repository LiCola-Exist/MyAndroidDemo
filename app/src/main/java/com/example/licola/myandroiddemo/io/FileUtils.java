package com.example.licola.myandroiddemo.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author LiCola
 * @date 2019/4/16
 */
public class FileUtils {

  private static final int BUFFER_SIZE = 4 * 1024;

  public static boolean writeFile(File outFile, InputStream inputStream) {
    outFile.deleteOnExit();


    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(outFile);
    } catch (FileNotFoundException e) {
    }

    if (fos==null){
      return false;
    }

    try {
      int len;
      byte[] buffer = new byte[BUFFER_SIZE];

      while ((len = inputStream.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return true;
  }
}
