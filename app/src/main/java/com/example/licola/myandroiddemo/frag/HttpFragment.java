package com.example.licola.myandroiddemo.frag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.Okio;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass. Use the {@link HttpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HttpFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  private String SIMPLE_URL = "http://publicobject.com/helloworld.txt";

  @BindView(R.id.btn_okhttp)
  Button btnOkhttp;
  @BindView(R.id.btn_retrofit)
  Button btnRetrofit;

  // TODO: Rename and change types of parameters
  private String mParam1;


  public HttpFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_http;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment HttpFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static HttpFragment newInstance(String param1) {
    HttpFragment fragment = new HttpFragment();
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
    View rootView = super.onCreateView(inflater,container ,savedInstanceState );

    rootView.findViewById(R.id.btn_okhttp).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onOkHttpClick(v);
      }
    });
    rootView.findViewById(R.id.btn_retrofit).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onRetrofitClick(v);
      }
    });
    rootView.findViewById(R.id.btn_http).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        runWorkThread(SIMPLE_URL);
      }
    });

    return rootView;
  }

  private void runWorkThread(String url){
    Thread thread=new Thread(makeUrlRunnable(url),"workThread");
    thread.start();
  }

  private Runnable makeUrlRunnable(final String urlStr) {

    return new Runnable() {
      @Override
      public void run() {
        URL url = null;
        try {
          url = new URL(urlStr);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }

        LLogger.d(url);

        HttpURLConnection urlConnection = null;
        try {
          urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
          e.printStackTrace();
        }


        try {
          urlConnection.setRequestMethod("GET");
          urlConnection.connect();

          int code = urlConnection.getResponseCode();
          LLogger.d(code);

          if (code==200){
            InputStream inputStream = urlConnection.getInputStream();
            Buffer buffer = new Buffer();
            buffer.writeAll(Okio.source(inputStream));
            String result = buffer.toString();
            LLogger.d(result);
          }else {
            if (301==code){
              String location = urlConnection.getHeaderField("Location");
              runWorkThread(location);
            }
          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

  }

  final static OkHttpClient client = new Builder()
      .eventListener(new EventListener() {
        @Override
        public void callStart(Call call) {
          super.callStart(call);
          LLogger.d();
        }

        @Override
        public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
            @Nullable Protocol protocol) {
          super.connectEnd(call, inetSocketAddress, proxy, protocol);
          LLogger.d();
        }

        @Override
        public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
            @Nullable Protocol protocol, IOException ioe) {
          super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
          LLogger.d();

        }

        @Override
        public void callEnd(Call call) {
          super.callEnd(call);
          LLogger.d();

        }

        @Override
        public void callFailed(Call call, IOException ioe) {
          super.callFailed(call, ioe);
          LLogger.d();
        }
      })
      .build();

  public void onOkHttpClick(final View view) {

    Request request = new Request.Builder().url(SIMPLE_URL).build();
//    Request request = new Request.Builder().url("https://me.d0575.net:4433/api/category").build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
          throw new IOException("Unexpected code " + response);
        }

        view.post(new Runnable() {
          @Override
          public void run() {
            view.setEnabled(false);
          }
        });
        LLogger.d(
            "Thread:" + Thread.currentThread().toString() + " body:" + response.body().string());
      }
    });
  }


  public void onRetrofitClick(View view) {
    Retrofit retrofit = new Retrofit.Builder().build();

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }


}
