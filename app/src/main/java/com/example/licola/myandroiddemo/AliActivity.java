package com.example.licola.myandroiddemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.licola.llogger.LLogger;
import java.util.Map;

public class AliActivity extends AndroidPopupActivity {

  @Override
  protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
    LLogger.d("OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ali);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }
}
