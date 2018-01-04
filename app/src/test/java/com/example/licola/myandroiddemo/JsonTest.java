package com.example.licola.myandroiddemo;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/a27.
 */

public class JsonTest {

  @Test
  public void JsonTest() throws JSONException {
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject("");
    } catch (JSONException e) {
      e.printStackTrace();
      assertEquals(null, jsonObject);
    }

    String input = null;
    try {
      jsonObject = new JSONObject(input);
    } catch (NullPointerException e) {
      e.printStackTrace();
      assertEquals(null, jsonObject);
    }
  }

  @Test
  public void testJSONTokener(){
    String jsonObjectString="{key:\"value\"}";
    JSONTokener jsonTokener=new JSONTokener(jsonObjectString);
  }
}
