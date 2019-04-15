package com.example.licola.myandroiddemo.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.aty.BottomSheetActivity;
import com.example.licola.myandroiddemo.aty.NoDisplayAty;
import com.example.licola.myandroiddemo.aty.ScrollingActivity;
import com.example.licola.myandroiddemo.aty.SoftKeyActivity;
import com.licola.llogger.LLogger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李可乐 on 2017/1/16 0016.
 */

public class TestFragment extends BaseFragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  TextView txtResult;
  Button btnStart;
  Button btnTest;

  boolean isPool = true;

  int size = 10;

  public TestFragment() {
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_test;
  }


  public static TestFragment newInstance(String key) {
    TestFragment fragment = new TestFragment();
    Bundle args = new Bundle();
    args.putString(ARG_SECTION_NUMBER, key);
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable final Bundle savedInstanceState) {
    View viewRoot = super.onCreateView(inflater, container, savedInstanceState);
    btnStart = viewRoot.findViewById(R.id.btn_start);
    btnTest = viewRoot.findViewById(R.id.btn_test);
    txtResult = viewRoot.findViewById(R.id.txt_result);

    viewRoot.findViewById(R.id.btn_value).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onClickValue();
      }
    });

    viewRoot.findViewById(R.id.btn_entry_scroll).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        new Thread(new Runnable() {
          @Override
          public void run() {
            Intent intent = new Intent(getContext(), ScrollingActivity.class);
            //传输数据 限制在1MB以下
//            byte[] value = new byte[1024 * 1024];
//        intent.putExtra("big", value);
//        startActivity(intent);

            Bundle bundle = new Bundle();
//            bundle.putByteArray("key", value);
            startActivity(intent, bundle);
          }
        }).start();
      }
    });

    viewRoot.findViewById(R.id.btn_entry_sheet).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), BottomSheetActivity.class);
        startActivity(intent);
      }
    });

    viewRoot.findViewById(R.id.btn_entry_soft).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), SoftKeyActivity.class);
        startActivity(intent);
      }
    });

    viewRoot.findViewById(R.id.btn_entry_empty).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), NoDisplayAty.class);
        startActivity(intent);
      }
    });

    viewRoot.findViewById(R.id.btn_block).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Thread.sleep(6000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    btnStart.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        if (isPool) {
          isPool = false;
          try {
            testThreadPool(size);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          isPool = true;
          try {
            testNewThread(size);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          size = size * 10;
        }
      }
    });

    btnTest.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        for (int i = 0; i < 100; i++) {
          testMapPutTime(1024);
        }
      }

    });

    return viewRoot;
  }

  private void testMapPutTime(int size) {
    int initSize = (int) (size / 0.75f + 1);
    HashMap<Integer, String> map = new HashMap<>(initSize);

    long begin;
    long end;
    System.out.println();
    begin = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      map.put(i, String.valueOf(i));
    }
    end = System.currentTimeMillis();

    LLogger.d("time=" + (end - begin));
  }

  private static class DoubleClass {

    private Integer name;

    public DoubleClass(Integer name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof DoubleClass)) {
        return false;
      }

      DoubleClass that = (DoubleClass) o;

      return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
      //return name != null ? name.hashCode() : 0;
      return Integer.valueOf(2).hashCode();
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("DoubleClass{");
      sb.append("name=").append(name);
      sb.append('}');
      return sb.toString();
    }
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    View rootView = getView();

    if (rootView instanceof ViewGroup) {
      Class<?> vClass = rootView.getClass().getSuperclass();
      try {
        Field mChildren = vClass.getDeclaredField("mChildren");
        mChildren.setAccessible(true);
        Object value = mChildren.get(rootView);
        LLogger.d(value);
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

//    txtResult.getHorizontallyScrolling();

    Class<?> tClass = txtResult.getClass().getSuperclass();
    try {
      Method hiddenMethod = tClass.getDeclaredMethod("getHorizontallyScrolling");
      hiddenMethod.setAccessible(true);
      Object invokeResult = hiddenMethod.invoke(txtResult);
      LLogger.d(invokeResult);

    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    try {

      //用Class类获取Class的getDeclaredMethod方法
      Method getDeclaredMethod = Class.class
          .getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);

      //通过持有的getDeclaredMethod方法 再反射得到目标类的方法
      Method hiddenMethod = (Method) getDeclaredMethod
          .invoke(TextView.class, "getHorizontallyScrolling", null);

      Object invokeResult = hiddenMethod.invoke(txtResult);
      LLogger.d(invokeResult);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }


  }

  boolean isWrap;

  public void onClickValue() {
    long time = System.currentTimeMillis();
    int sum = 0;
    if (isWrap) {
      isWrap = false;
      for (int i = 0; i < 100000; i++) {
        sum = +i;
      }
    } else {
      isWrap = true;
      for (int i = 0; i < 100000; i++) {
        Integer integer = i;
        sum = +integer;
      }
    }

    LLogger.d(" time =" + (System.currentTimeMillis() - time) + " sum=" + sum);
  }

  /**
   * @param @throws InterruptedException
   * @Description: java线程池实现
   * @author dapengniao
   * @date 2016年4月14日 上午10:16:23
   */
  public void testThreadPool(int size) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(size);
    ExecutorService executorService = Executors.newCachedThreadPool();
    long bg = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      Runnable command = new TestRunnable(countDownLatch);
      executorService.execute(command);
    }
    countDownLatch.await();
    LLogger.d("testThreadPool:" + (System.currentTimeMillis() - bg));
  }

  /**
   * @param @throws InterruptedException
   * @Description: 创建java线程实现
   * @author dapengniao
   * @date 2016年4月14日 上午10:16:a31
   */
  public void testNewThread(int size) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(size);
    long bg = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      Runnable command = new TestRunnable(countDownLatch);
      Thread thread = new Thread(command);
      thread.start();
    }
    countDownLatch.await();
    LLogger.d("testNewThread:" + (System.currentTimeMillis() - bg));
  }

  private static class TestRunnable implements Runnable {

    private final CountDownLatch countDownLatch;

    TestRunnable(CountDownLatch countDownLatch) {
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      //      LLogger.d("Thread-Log,调用了创建线程");
      countDownLatch.countDown();
    }
  }

  private void testNewThread(int size, Runnable runnable) {
    for (int i = 0; i < size; i++) {
      new Thread(runnable).start();
    }
  }

  private void testPoolThread(int size, Runnable runnable) {
    ExecutorService service = Executors.newCachedThreadPool();
    for (int i = 0; i < size; i++) {
      service.execute(runnable);
    }
  }

  private void testFor(int size) {
    List<Integer> result = createLinke(size);
    long start;
    long end;

    txtResult.append("\n result=" + size);

    start = System.currentTimeMillis();
    for (int i = 0; i < result.size(); i++) {

    }
    end = System.currentTimeMillis();
    long foriTime = end - start;
    txtResult.append("\n foriTime=" + foriTime);

    start = System.currentTimeMillis();
    for (Integer item :
        result) {
    }
    end = System.currentTimeMillis();
    long foreachTime = end - start;
    txtResult.append("\n foreachTime=" + foreachTime);

    start = System.currentTimeMillis();
    Iterator<Integer> iterator = result.iterator();
    while (iterator.hasNext()) {
      iterator.next();
    }
    end = System.currentTimeMillis();
    long iteratorTime = end - start;
    txtResult.append("\n iteratorTime=" + iteratorTime);

    txtResult.append("end\n");
  }

  public ArrayList<Integer> createList(int size) {
    ArrayList<Integer> arrayList = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      arrayList.add(i);
    }
    return arrayList;
  }

  public LinkedList<Integer> createLinke(int size) {
    LinkedList<Integer> integers = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      integers.add(i);
    }
    return integers;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
