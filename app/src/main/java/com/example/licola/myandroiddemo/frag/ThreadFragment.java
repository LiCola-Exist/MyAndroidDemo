package com.example.licola.myandroiddemo.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 */
public class ThreadFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  public ThreadFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ThreadFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ThreadFragment newInstance(String param1) {
    ThreadFragment fragment = new ThreadFragment();
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
    View viewRoot = inflater.inflate(R.layout.fragment_thread, container, false);
    Button btnNewThread = viewRoot.findViewById(R.id.btn_new_thread);
    btnNewThread.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        newThreadPostUi();
      }
    });

    View btnUncaughtException = viewRoot.findViewById(R.id.btn_uncaught_exception);
    btnUncaughtException.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        uncaughtException();
      }
    });

    return viewRoot;
  }


  private void uncaughtException() {
    final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread
        .getDefaultUncaughtExceptionHandler();

    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
        LLogger.d("捕获当前线程异常 然后转发给默认的异常处理器，可以作为crash捕获入口");

        defaultUncaughtExceptionHandler.uncaughtException(t, e);
      }
    });

    throw new UnsupportedOperationException("故意抛出的非受检异常");

  }

  private void newThreadPostUi() {

    final Handler workHandler;

    HandlerThread handlerThread = new HandlerThread("worker");
    handlerThread.start();

    workHandler = new Handler(handlerThread.getLooper());

    workHandler.post(new Runnable() {
      @Override
      public void run() {
        boolean isMain = getActivity().getMainLooper() == workHandler.getLooper();
        LLogger.d(Thread.currentThread(), getActivity().getMainLooper(), isMain);
        Toast.makeText(getContext(), "子线程发出", Toast.LENGTH_SHORT).show();
      }
    });

  }
}
