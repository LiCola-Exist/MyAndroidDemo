package com.example.licola.myandroiddemo.aty;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;

public class BottomSheetActivity extends BaseActivity {


  private BottomSheetBehavior behavior;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottom_sheet);
    final View view = findViewById(R.id.nested_view);

    View viewButtom = findViewById(R.id.txt_main);
    viewButtom.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean operate = !view.isSelected();
        if (operate) {
          behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {

          behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        view.setSelected(operate);
      }
    });

    behavior = BottomSheetBehavior.from(view);
    behavior.setHideable(true);
    behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        String log;
        switch (newState) {
          case BottomSheetBehavior.STATE_DRAGGING:
            log = "STATE_DRAGGING";
            break;
          case BottomSheetBehavior.STATE_SETTLING:
            log = "STATE_SETTLING";
            break;
          case BottomSheetBehavior.STATE_EXPANDED:
            log = "STATE_EXPANDED";
            break;
          case BottomSheetBehavior.STATE_COLLAPSED:
            log = "STATE_COLLAPSED";
            break;
          case BottomSheetBehavior.STATE_HIDDEN:
            log = "STATE_HIDDEN";
            break;
          default:
            log = "default";
            break;
        }
        LLogger.d(log);
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        if (slideOffset>0){
          int topOffset= (int) (200*slideOffset);
          bottomSheet.setPadding(0,topOffset,0 ,0 );
        }
          LLogger.d(slideOffset);
      }
    });

  }
}
