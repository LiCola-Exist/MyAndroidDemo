package com.example.licola.myandroiddemo.collections;

import android.support.v4.util.SparseArrayCompat;
import org.junit.Test;

/**
 * Created by LiCola on 2018/3/16.
 */

public class MapTest {

  @Test
  public void map() {
    SparseArrayCompat<String> sparse=new SparseArrayCompat<>();

    sparse.put(100,"a0");
    sparse.put(200,"a1");
    sparse.put(700,"a2");
    sparse.put(10,"a3");

    System.out.println(sparse.size());
  }
}
