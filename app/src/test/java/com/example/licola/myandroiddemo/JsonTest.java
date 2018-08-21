package com.example.licola.myandroiddemo;

import static org.junit.Assert.assertEquals;

import com.example.licola.myandroiddemo.data.model.JsonArrayType;
import com.example.licola.myandroiddemo.data.model.JsonType;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/a27.
 */

public class JsonTest {

  private final static String jsonWrap = "{\n"
      + "    \"status\": 0,\n"
      + "    \"msg\": \"\",\n"
      + "    \"resultArray\": [\n"
      + "        {\n"
      + "            \"type\": \"JsonArrayItem\"\n"
      + "        }\n"
      + "    ],\n"
      + "    \"resultObj\": {\n"
      + "        \"type\": \"JsonObject\"\n"
      + "    },\n"
      + "    \"resultStr\": \"String\"\n"
      + "}";

  private final static String jsonArrayWrap="[\n"
      + "    {\n"
      + "        \"type\": \"JsonArrayItem\"\n"
      + "    }\n"
      + "]";


  @Test
  public void jsonTypeTest() {

    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(jsonWrap);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    System.out.println(jsonObject);

    Object obj = jsonObject.opt("resultObj");
    assertEquals(true, obj instanceof JSONObject);

    Object array = jsonObject.opt("resultArray");
    assertEquals(true, array instanceof JSONArray);
  }

  @Test
  public void gsonTest() throws IOException {
    Gson gson = new Gson();
    JsonType jsonType = gson.fromJson(jsonWrap, JsonType.class);
    System.out.println(jsonType.toString());

    TypeAdapter<JsonType> adapter = gson.getAdapter(JsonType.class);

    JsonType jsonType1 = adapter.fromJson(jsonWrap);

    System.out.println(jsonType1.toString());

    Type type = new TypeToken<List<JsonArrayType>>() {
    }.getType();
    List<JsonArrayType> list=gson.fromJson(jsonArrayWrap,type);
    for (JsonArrayType jsonArrayType : list) {
      System.out.println("json array :"+jsonArrayType.getType());
    }
  }

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
  public void testJSONTokener() {
    String jsonObjectString = "{key:\"value\"}";
    JSONTokener jsonTokener = new JSONTokener(jsonObjectString);
  }
}
