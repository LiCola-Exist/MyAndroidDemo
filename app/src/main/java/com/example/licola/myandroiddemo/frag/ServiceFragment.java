package com.example.licola.myandroiddemo.frag;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.messenger.MessengerService;

/**
 * A simple {@link Fragment} subclass. Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;




  public ServiceFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_service;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ServiceFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ServiceFragment newInstance(String param1) {
    ServiceFragment fragment = new ServiceFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  private Messenger mService;

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      Message message = Message.obtain(null, 100);
      Bundle data = new Bundle();
      data.putString("msg", "hello this is client");
      message.setData(data);
      try {
        mService.send(message);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

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
    View viewRoot = super.onCreateView(inflater, container, savedInstanceState);

    return viewRoot;
  }

  @OnClick(R.id.btn_bind_service)
  public void onBtnBindServiceClick(View view) {
    Intent intent = new Intent(getMContext(), MessengerService.class);
    getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @OnClick(R.id.btn_unbind_service)
  public void onBtnUnBindServiceClick(View view) {
    getActivity().unbindService(mConnection);
  }
}
