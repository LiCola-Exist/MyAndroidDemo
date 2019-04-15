package com.example.licola.myandroiddemo.frag;


import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.example.licola.myandroiddemo.R;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass. Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends BaseFragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  // TODO: Rename and change types of parameters
  private String mParam1;


  @BindView(R.id.surface_view)
  SurfaceView surfaceView;

  public CameraFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_camera;
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment CameraFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static CameraFragment newInstance(String param1) {
    CameraFragment fragment = new CameraFragment();
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
    View rootView = super.onCreateView(inflater, container, savedInstanceState);

    Camera camera = Camera.open();

    camera.setPreviewCallback(new PreviewCallback() {
      @Override
      public void onPreviewFrame(byte[] data, Camera camera) {

      }
    });

    SurfaceHolder holder = surfaceView.getHolder();
    holder.addCallback(new Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        try {
          camera.setPreviewDisplay(holder);
          camera.startPreview();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        camera.release();
      }
    });

    return rootView;
  }

}
