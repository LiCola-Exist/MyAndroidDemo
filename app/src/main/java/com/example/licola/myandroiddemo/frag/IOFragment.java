package com.example.licola.myandroiddemo.frag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import java.io.File;
import java.io.IOException;
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
public class IOFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";

  private String mParam1;


  public IOFragment() {
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_io;
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
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    File file = new File(getContext().getCacheDir(), "OkioTemp.txt");
    LLogger.d("file:" + file.toString());
    String readData = null;
    try {
      File fileWrite = writeIO(file);
      readData = readIO(fileWrite);
    } catch (IOException e) {
      e.printStackTrace();
    }
    LLogger.d("readData:" + readData);


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
