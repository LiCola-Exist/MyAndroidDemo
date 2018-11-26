package com.example.licola.myandroiddemo.aty;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.licola.myandroiddemo.frag.NestedScrollFragment;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import com.licola.route.annotation.Route;
import java.util.List;

@Route(path = "scrolling")
public class ScrollingActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
        postDelayRunnable();
      }
    });

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.container,new NestedScrollFragment()).commit();

    ActivityManager systemService = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningAppProcessInfo> runningAppProcesses = systemService.getRunningAppProcesses();
    for (RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
      LLogger.d(runningAppProcess.lru);
    }
  }

  private Handler handler=new Handler();

  private void postDelayRunnable() {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        LLogger.d("延迟任务执行");
      }
    },10000);
  }
}
