package com.example.licola.myandroiddemo.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.licola.myandroiddemo.R;
import com.licola.llogger.LLogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 特殊的内存泄露：固定线程池+ThreadLocal，核心线程不会释放导致，内部ThreadLocalMap的key-value中的value持有外部页面引用
 */
public class MemoryActivity extends BaseActivity {

  private static final int SIZE = 4;

  private final ThreadLocal<Context> contextThreadLocal = new ThreadLocal<Context>() {
//    @Override
//    protected Context initialValue() {
//      return mContext;
//    }
  };

  private final ExecutorService executors = new ThreadPoolExecutor(SIZE, SIZE,
      10L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<Runnable>());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_memory);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    /**
     * 解决方法1：让核心线程 可以等待超时然后回收
     */
    if (executors instanceof ThreadPoolExecutor) {
      ((ThreadPoolExecutor) executors).allowCoreThreadTimeOut(true);
    }

    for (int i = 0; i < SIZE; i++) {
      executors.execute(new Runnable() {
        @Override
        public void run() {
          Context context = contextThreadLocal.get();

          if (context == null) {
            context = mContext;
            contextThreadLocal.set(mContext);
          }

          ClassLoader classLoader = context.getClassLoader();
          LLogger.d(classLoader);
          doPrivate(context);

          /**
           * 解决方法2：及时删除ThreadLocal 让线程中的map解除value中的强引用关联
           */
//          contextThreadLocal.remove();
        }
      });
    }
  }


  private void doPrivate(Context context) {
    LLogger.d(context, Thread.currentThread());
  }

}
