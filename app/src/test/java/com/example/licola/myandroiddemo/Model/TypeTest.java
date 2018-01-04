package com.example.licola.myandroiddemo.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by LiCola on 2017/6/9.
 */
public class TypeTest {

  @Test
  public void cast(){
    Type type=Type.valueOf("Action1");
    assertEquals(type,Type.Action);
  }
}