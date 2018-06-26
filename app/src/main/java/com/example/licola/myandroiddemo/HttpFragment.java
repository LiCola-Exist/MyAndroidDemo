package com.example.licola.myandroiddemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.licola.llogger.LLogger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HttpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HttpFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  @BindView(R.id.btn_okhttp)
  Button btnOkhttp;
  @BindView(R.id.btn_retrofit)
  Button btnRetrofit;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
  private String mParam1;


  public HttpFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
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
    View view = inflater.inflate(R.layout.fragment_http, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
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

  @OnClick(R.id.btn_okhttp)
  public void onOkHttpClick(final View view) {

    Request request =
        new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

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

  Retrofit retrofit=new Retrofit.Builder().build();


  @OnClick(R.id.btn_retrofit)
  public void onRetrofitClick(View view){

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }


}
