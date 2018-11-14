package com.example.licola.myandroiddemo;


import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.licola.llogger.LLogger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * A simple {@link Fragment} subclass. Use the {@link ToastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToastFragment extends BaseFragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";

  private String mParam1;


  public ToastFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @return A new instance of fragment ToastFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ToastFragment newInstance(String param1) {
    ToastFragment fragment = new ToastFragment();
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
    View rootView = inflater.inflate(R.layout.fragment_toast, container, false);

    onBindNormalToast(rootView.findViewById(R.id.btn_toast_normal));
    onBindGravityToast(rootView.findViewById(R.id.btn_toast_gravity));
    onBindViewToast(rootView.findViewById(R.id.btn_toast_view));
    onBindViewPopup(
        rootView.findViewById(R.id.btn_popup_view_left),
        rootView.findViewById(R.id.btn_popup_view_center),
        rootView.findViewById(R.id.btn_popup_view_right)
    );


    onBindSnackBar(rootView,rootView.findViewById(R.id.btn_snack_bar));

    return rootView;
  }

  private void onBindSnackBar(final View rootView,View viewBtn) {

    viewBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        Snackbar snackbar = Snackbar.make(rootView, "SnackBar显示", Snackbar.LENGTH_SHORT)
            .setAction("action", new OnClickListener() {
              @Override
              public void onClick(View v) {
                LLogger.d();
              }
            })
            .addCallback(new BaseCallback<Snackbar>() {
              @Override
              public void onDismissed(Snackbar transientBottomBar, int event) {
                LLogger.d(transientBottomBar,event);
              }

              @Override
              public void onShown(Snackbar transientBottomBar) {
                LLogger.d(transientBottomBar);
              }
            });

        snackbar.show();
      }
    });
  }

  private void onBindViewPopup(final View viewLeft, final View viewCenter, final View viewRight) {

    final Context context = getContext();
    LayoutInflater inflate = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View toastView = inflate.inflate(R.layout.toast_view, null);
    TextView tvContent = toastView.findViewById(R.id.tv_content);
    tvContent.setText("附着在目标View的Popup");

    final PopupWindow popupWindow = new PopupWindow(toastView, LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT);

    popupWindow.setOutsideTouchable(true);
    final int popupHeight = PixelUtils.dp2px(context, 50);
    final int anchorHeight = PixelUtils.dp2px(context, 48);
    final int offsetY = -(popupHeight + anchorHeight);

    int measureSpec = MeasureSpec
        .makeMeasureSpec(MeasureSpec.getSize(LayoutParams.WRAP_CONTENT), MeasureSpec.UNSPECIFIED);
    toastView.measure(measureSpec, measureSpec);

    final int measuredWidth = toastView.getMeasuredWidth();
    LLogger.d(measuredWidth);

    viewCenter.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int targetWidth = v.getWidth();
        if (targetWidth < measuredWidth) {
          int offsetX = (targetWidth - measuredWidth) / 2;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        } else {
          int offsetX = (targetWidth - measuredWidth) / 2;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        }
      }
    });

    viewLeft.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int targetWidth = v.getWidth();
        if (targetWidth < measuredWidth) {
          int offsetX = 0;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        } else {
          int offsetX = (targetWidth - measuredWidth) / 2;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        }
      }
    });

    viewRight.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int targetWidth = v.getWidth();
        if (targetWidth < measuredWidth) {
          int offsetX = targetWidth - measuredWidth;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        } else {
          int offsetX = (targetWidth - measuredWidth) / 2;
          popupWindow.showAsDropDown(v, offsetX, offsetY);
        }

      }
    });


  }

  private void onBindViewToast(View btnView) {
    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflate = (LayoutInflater)
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflate.inflate(R.layout.toast_view, null);
        TextView tvContent = toastView.findViewById(R.id.tv_content);
        tvContent.setText("自定义View的Toast");
        toast.setView(toastView);
        toast.show();
      }
    });

  }


  private void onBindGravityToast(View btnView) {
    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast toast = Toast.makeText(getContext(), "内容", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    });
  }

  private void onBindNormalToast(final View btnView) {

    btnView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean enabled = checkNotificationEnabled(btnView.getContext());
        LLogger.d(enabled);
        Toast.makeText(getContext(), "内容", Toast.LENGTH_SHORT)
            .show();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * 检查通知栏权限有没有开启
   */
  public static boolean checkNotificationEnabled(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
          .areNotificationsEnabled();
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
      ApplicationInfo appInfo = context.getApplicationInfo();
      String pkg = context.getApplicationContext().getPackageName();
      int uid = appInfo.uid;

      try {
        Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
        Method checkOpNoThrowMethod = appOpsClass
            .getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
        Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
        int value = (Integer) opPostNotificationValue.get(Integer.class);
        return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
      } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | RuntimeException | ClassNotFoundException ignored) {
        return true;
      }
    } else {
      return true;
    }
  }

}
