package com.example.licola.myandroiddemo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.licola.myandroiddemo.utils.BitmapUtils;
import com.example.licola.myandroiddemo.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IOFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IOFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";

  private String mParam1;


  public IOFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment IOFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static IOFragment newInstance(String param1) {
    IOFragment fragment = new IOFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View viewRoot = inflater.inflate(R.layout.fragment_io, container, false);

    return viewRoot;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    File file = new File(getContext().getCacheDir(), "OkioTemp.txt");
    Logger.d("file:" + file.toString());
    String readData = null;
    try {
      File fileWrite = writeIO(file);
      readData = readIO(fileWrite);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Logger.d("readData:" + readData);



  }

  private String readIO(File file) throws IOException {

    Source source = Okio.source(file);
    BufferedSource bufferedSource ;
    bufferedSource = Okio.buffer(source);


    StringBuilder stringBuilder=new StringBuilder();
    try {
      stringBuilder.append(bufferedSource.readUtf8());
    } finally {
      Util.closeQuietly(bufferedSource);
    }

    return stringBuilder.toString();

  }

  private File writeIO(File file) throws IOException {

    if (!file.exists()) {
      boolean newFile = file.createNewFile();
    }

    Sink sink = Okio.sink(file);
    BufferedSink bufferedSink = Okio.buffer(sink);
    try {
      bufferedSink.writeUtf8("ok io write date");
    } finally {
      Util.closeQuietly(bufferedSink);
    }
    return file;

  }
}
