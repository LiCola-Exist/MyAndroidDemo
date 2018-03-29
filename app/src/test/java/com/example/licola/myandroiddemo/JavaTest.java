package com.example.licola.myandroiddemo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/4/19.
 */

public class JavaTest {


  public void work(GetInput input){
    System.out.printf("只调用方法 不获取输入");
  }

  public interface GetInput{
    String input();
  }

  @Test
  public void methodTest(){


    JavaTest javaTest=new JavaTest();
    javaTest.work(new GetInput() {
      @Override public String input() {
        throw new RuntimeException("不应该调用");
        //return "input";
      }
    });
  }

  @Test
  public void javaTest() throws Exception {
    int[] ints = new int[]{1, 2};
    Integer[] integers = new Integer[]{3, 4};

    String[] strings = new String[]{"s1", "s2"};
    System.out.println("data " + ints.getClass());

    List list = new ArrayList();
    list.add(1);
    list.add("2");


  }

  @Test
  public void judge() {
    Integer result = tryTest();
    System.out.println("result= " + result);
    char c = '0';
    Integer wrapData = 12700;
    baseChange(c);
    wrapChange(wrapData);
    System.out.println("result= " + c);
    System.out.println("result= " + wrapData);
  }

  private void baseChange(char date) {
    date = '1';
  }

  private void wrapChange(Integer date) {
    date = date * 10;
  }

  private int tryTest() {
    try {
      System.out.println("try block");
      return 10;
    } finally {
      System.out.println("finally block");
      return 100;
    }

  }
}
