package com.example.licola.myandroiddemo.current;

import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

/**
 * Created by LiCola on 2018/2/1.
 */

public class ConcurrentHashMapTest {
  @Test
  public void MapTest() {
    ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();
    System.out.println(map.size());
  }
}
