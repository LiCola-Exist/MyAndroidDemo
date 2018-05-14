package com.example.licola.myandroiddemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.licola.myandroiddemo.utils.Logger;
import io.socket.client.IO;
import io.socket.emitter.Emitter.Listener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocketFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


//  private static final String HOST = "192.168.2.16";
  private static final String HOST = "192.168.2.3";
  private static final int PORT = 9999;
//  private static final int PORT = 4543;
  private static final String HOST_HTTP = "http://192.168.2.10:"+PORT;
//  private static final String HOST_HTTP = "http://192.168.2.16:9999";
//  private static final String HOST_HTTP = "https://wxapp-socket.uboxs.com";


  EditText editInput;
  Button btnConnect;
  Button btnSend;
  Button btnDisconnect;
  TextView txtReceive;
  ExecutorService executorService = Executors.newCachedThreadPool();

  private PrintWriter printWriter;
  private BufferedReader in;
  private String receiveMsg;

  io.socket.client.Socket socketClient;

  public SocketFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment SocketFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SocketFragment newInstance(String param1) {
    SocketFragment fragment = new SocketFragment();
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
    View viewRoot = inflater.inflate(R.layout.fragment_socket, container, false);
    editInput = viewRoot.findViewById(R.id.edit_input);
    btnConnect = viewRoot.findViewById(R.id.btn_connect);
    btnSend = viewRoot.findViewById(R.id.btn_send);
    btnDisconnect = viewRoot.findViewById(R.id.btn_disconnect);
    txtReceive = viewRoot.findViewById(R.id.txt_receive);

    return viewRoot;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    btnConnect.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//        executorService.execute(new ConnectToService());
        executorService.execute(new SocketClient());
      }
    });

    btnSend.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String input = editInput.getText().toString();
        executorService.execute(new SendToService(input));
        editInput.setText("");
      }
    });

    btnDisconnect.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        executorService.execute(new SendToService("0"));
      }
    });
  }


  public class SocketClient implements Runnable {

    @Override
    public void run() {
      try {

        IO.Options opts = new IO.Options();
        opts.reconnection = false;
        opts.query = String.format("auth_token=%s", "ObLV0Yy9SHrBydly9DbfWDfTmUecO8Cs");
        opts.timeout = 2000;

        socketClient = IO.socket(HOST_HTTP,opts);
        socketClient.on(io.socket.client.Socket.EVENT_CONNECT, new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket 连接事件");
            socketClient.emit("post", "hello");
          }
        }).on("post", new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket event post:"+args[0]);
          }
        }).on("event-send", new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket event send:"+args[0]);
          }
        }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket 断开连接事件");
          }
        }).on(io.socket.client.Socket.EVENT_CONNECT_ERROR, new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket 连接错误");
          }
        }).on(io.socket.client.Socket.EVENT_ERROR, new Listener() {
          @Override
          public void call(Object... args) {
            Logger.d("socket 错误");
          }
        });

        socketClient.connect();

      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  public class SendToService implements Runnable {

    private String msg;

    public SendToService(String msg) {
      this.msg = msg;
    }

    @Override
    public void run() {
//      printWriter.println(msg);
      socketClient.emit("post", msg);
    }
  }

  public class ConnectToService implements Runnable {

    @Override
    public void run() {
      try {
        Socket socket = new Socket(HOST, PORT);
        socket.setSoTimeout(600000);
        printWriter = new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        handleReceiveMsg();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void handleReceiveMsg() {
    try {
      while (true) {
        if ((receiveMsg = in.readLine()) != null) {
          Logger.d("客户端收到 receive:" + receiveMsg);
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              txtReceive.append(receiveMsg + "\n");
            }
          });
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
