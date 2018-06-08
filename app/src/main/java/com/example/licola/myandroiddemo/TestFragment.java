package com.example.licola.myandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.licola.llogger.LLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李可乐 on 2017/1/16 0016.
 */

public class TestFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  TextView txtResult;
  Button btnStart;
  Button btnTest;

  boolean isPool = true;

  int size = 10;

  public TestFragment() {
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
    View rootView = inflater.inflate(R.layout.fragment_test, container, false);
    ButterKnife.bind(this,rootView);
    btnStart = (Button) rootView.findViewById(R.id.btn_start);
    btnTest = (Button) rootView.findViewById(R.id.btn_test);
    txtResult = (TextView) rootView.findViewById(R.id.txt_result);
    Button button= (Button) rootView.findViewById(R.id.entry);
    button.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent=new Intent(getContext(),ScrollingActivity.class);
        startActivity(intent);
      }
    });

    testMap();

    LLogger.d(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
        DateUtils.FORMAT_SHOW_TIME));
    LLogger.d(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
        DateUtils.FORMAT_ABBREV_ALL));
    LLogger.d(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
        DateUtils.FORMAT_ABBREV_WEEKDAY
            | DateUtils.FORMAT_ABBREV_ALL
            | DateUtils.FORMAT_ABBREV_TIME));

    btnStart.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        //                size = size * 10;
        //                if (size==1000000)size=10000;
        //        testFor(size);
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
      @Override public void onClick(View v) {
        for (int i = 0; i < 100; i++) {
          testMapPut(1024);
        }
      }

      private void testMapPut(int size) {
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
    });



    return rootView;
  }

  private static class DoubleClass{
    private Integer name;

    public DoubleClass(Integer name) {
      this.name = name;
    }

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof DoubleClass)) return false;


      DoubleClass that = (DoubleClass) o;

      return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override public int hashCode() {
      //return name != null ? name.hashCode() : 0;
      return Integer.valueOf(2).hashCode();
    }

    @Override public String toString() {
      final StringBuilder sb = new StringBuilder("DoubleClass{");
      sb.append("name=").append(name);
      sb.append('}');
      return sb.toString();
    }
  }

  private void testMap() {
    HashMap<Integer,String> map=new HashMap<>();
    for (int i = 0; i < 10; i++) {
      map.put(i,"data"+i);
    }
    map.get(2);
    map.containsValue("data2");
    map.remove(2);

    SparseArray<String> stringSparseArray=new SparseArray<>();
    stringSparseArray.put(1,"23");

    //HashMap<DoubleClass,String> map=new HashMap<>();
    //map.put(new DoubleClass(2),"double2_0");
    //map.put(new DoubleClass(3),"double2_1");
    //LLogger.d("sub:"+(5/2));
    //String s = map.get(new DoubleClass(2));
    //LLogger.d("first:"+s);
    //String s1 = map.get(new DoubleClass(3));
    //LLogger.d("second:"+s1);
    //LLogger.d(map.size());

    Hashtable<String,Integer> hashtable=new Hashtable<>();
    hashtable.put("s",1);
    ConcurrentHashMap<String,Integer> concurrentHashMap=new ConcurrentHashMap<>();
    concurrentHashMap.put("1",2);


  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  boolean isWrap;

  @OnClick(R.id.btn_value)
  public void onClickValue(){
    long time=System.currentTimeMillis();
    int sum=0;
    if (isWrap){
      isWrap=false;
      for (int i = 0; i < 100000; i++) {
        sum=+i;
      }
    }else {
      isWrap=true;
      for (int i = 0; i < 100000; i++) {
        Integer integer=i;
        sum=+integer;
      }
    }

    LLogger.d(" time ="+(System.currentTimeMillis()-time)+" sum="+sum);
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
