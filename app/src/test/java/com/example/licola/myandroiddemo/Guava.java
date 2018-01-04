package com.example.licola.myandroiddemo;

import static org.junit.Assert.assertEquals;

import android.support.annotation.Nullable;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/3.
 */

public class Guava {

  String result;
  String[] results;

  @Test
  public void CharMatcher() {

    result = CharMatcher.DIGIT.retainFrom("some word 123 more");
    System.out.println("CharMatcher.DIGIT.retainFrom result= " + result);
    result = CharMatcher.DIGIT.removeFrom("some word 123 more");
    System.out.println("CharMatcher.DIGIT.removeFrom result= " + result);
    result = CharMatcher.DIGIT.replaceFrom("some word 123 more", "replace ");
    System.out.println("CharMatcher.DIGIT.replaceFrom result= " + result);

  }

  @Test
  public void JoinerAndSplitter() {
    result = Ints.join("|", 1, 2, 3, 4, 5, 6);
    System.out.println("Ints.join result= " + result);
    result = Strings.repeat("+abd+", 2);
    System.out.println("Strings.repeat result= " + result);
    System.out.println("========");

    String[] stringList = {"aa", "bb", "cc"};
    result = Joiner.on("/").join(stringList);
    System.out.println("Joiner.on result=  " + result);
    result = Ints.join("/", 1, 2, 3);
    System.out.println("Ints.join result=  " + result);
    System.out.println();
    System.out.println("========");

    String target = "apple,,android,wp,";
    results = target.split(",");
    System.out.print("split results=  ");
    System.out.print(Arrays.toString(results));
    System.out.println();
    System.out.println("========");
    Iterable<String> iterable = Splitter.on(",").omitEmptyStrings().split(target);
    System.out.print("Splitter results=");
    for (String item : iterable) {
      System.out.print(" item : "+item);
    }

    System.out.println();
    System.out.println("========");

    String str = "key1: 1; key2: 2  ; key3: 3";
    Map<String, String> map = Splitter.on(';')
        .trimResults()
        .withKeyValueSeparator(":")
        .split(str);
    for (Entry<String, String> itemEntry :
        map.entrySet()) {
      System.out.print(" key= "+itemEntry.getKey()+" value= "+itemEntry.getValue());
    }
    System.out.println();
    System.out.println("========");

  }

  @Test
  public void function(){
    Map<String,Integer> data=Maps.newHashMap();
    data.put("key1",1);
    data.put("key2",2);
    data.put("key3",3);
    Map<String,Integer> resultMap= Maps.transformValues(data, new Function<Integer, Integer>() {
      @Nullable
      @Override
      public Integer apply(Integer input) {
        return input*10;
      }
    });

    for (Entry<String, Integer> itemEntry : resultMap.entrySet()) {
      System.out.print(" key= "+itemEntry.getKey()+" value= "+itemEntry.getValue());
    }
  }

  @Test
  public void range(){
    assertEquals(true,Range.closed(1,3).contains(1));
  }

  @Test
  public void multi() {
    Multimap<String, String> multimap = ArrayListMultimap.create();
    multimap.put("apple", "iphone6");
    multimap.put("apple", "iphone6s");
    multimap.put("apple", "iphone7");
    multimap.put("android", "nexus5");
    multimap.put("android", "nexus6");

    System.out.println("size=" + multimap.size());
    System.out.println("========");
    Collection<String> apples = multimap.get("apple");
    System.out.println(apples);
    System.out.println("========");

    Collection<String> android = multimap.get("android");
    System.out.println(android);
    System.out.println("========");

    for (String item : multimap.values()) {
      System.out.println("value=" + item);
    }
    System.out.println("========");

    multimap.remove("apple", "iphone7");
    Collection<String> apples1 = multimap.get("apple");
    System.out.println(apples1);
    System.out.println("========");


  }
}
