package com.example.licola.myandroiddemo;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import java.util.HashMap;
import java.util.Locale;
import org.junit.Test;

/**
 * Created by LiCola on 2017/6/12.
 */

public class CollectionsTest {



  @Test
  public void MapTest() {

    int size=1024;
    int times=100;

    long total=0;
    long bigTime=0;
    for (int i = 0; i < times; i++) {
      long time=initMapAndPut(i,size);
      total+= time;
      if (time>0){
        bigTime++;
      }
    }
    System.out.println("average time="+(total/times)+" >0 times = "+bigTime);
  }

  private long initMapAndPut(int position ,int size) {
    int initSize= (int) ((size/0.75f)+1);
    HashMap<Integer, String> map = new HashMap<>(initSize);
    long begin;
    long end;


    long beginMemory= Runtime.getRuntime().totalMemory();
    begin = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      map.put(i, String.valueOf(i));
    }
    end = System.currentTimeMillis();
    long endMemory= Runtime.getRuntime().totalMemory();
    long time=end-begin;
    long memory=endMemory-beginMemory;
    System.out.println(String.format(Locale.CHINA,"position = %d userTime = %d userMemory = %d",position,time,memory));


    return time;
  }


  @Test
  public void userOtherMap(){

    SparseArray<String> sparseArray=new SparseArray<>();
    SparseArrayCompat<String> sparseArrayCompat=new SparseArrayCompat<>();
    sparseArrayCompat.append(100,"v1");
    sparseArrayCompat.append(200,"v2");
    sparseArrayCompat.append(90,"v3");
    sparseArrayCompat.append(80,"v4");


    int size = sparseArrayCompat.size();

    HashMap<Integer,String> hashMap=new HashMap<>();
    hashMap.put(1,"v1");
    System.out.println(hashMap.size());

    System.out.println("sparseArrayCompat size:"+size);
    ArrayMap<String,Integer> arrayMap=new ArrayMap<>();


//    System.out.println("idealByteArraySize 10:"+idealByteArraySize(10));
//    System.out.println("idealByteArraySize 10 calc:"+idealByteArraySize(10 * 4) / 4);

    System.out.println("1>>>1:"+(1>>>1)+" ~0:"+(~0)+" ~4:"+(~4));

    long totalMemory = Runtime.getRuntime().totalMemory();
    long maxMemory = Runtime.getRuntime().maxMemory();
    long freeMemory = Runtime.getRuntime().freeMemory();
    System.out.println("totalMemory:"+(totalMemory>>20)+" maxMemory:"+(maxMemory>>20)+" freeMemory:"+(freeMemory>>20));

    int[] srcs=new int[6];
    srcs[0]=10;
    srcs[1]=11;
    srcs[2]=12;
    srcs[3]=13;
    for (int i = 0; i < srcs.length; i++) {
      System.out.println("src index:"+i+" value:"+srcs[i]);
    }
    System.arraycopy(srcs,1,srcs,2,3);
    System.out.println("copy after-->");
    for (int i = 0; i < srcs.length; i++) {
      System.out.println("src index:"+i+" value:"+srcs[i]);
    }
  }

  public static int idealByteArraySize(int need) {
    for (int i = 4; i < 32; i++)
      if (need <= (1 << i) - 12)
        return (1 << i) - 12;

    return need;
  }



}
