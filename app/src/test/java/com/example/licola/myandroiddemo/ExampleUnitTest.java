package com.example.licola.myandroiddemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.licola.myandroiddemo.utils.RandomUtils;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

  @Test
  public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
    double sin = Math.sin(Math.toRadians(90));
    double sin1 = Math.sin(Math.toRadians(0));
    System.out.println("sin:"+sin+" sin1:"+sin1);
  }

  @Test
  public void randomString() throws Exception {
    System.out.println("result=" + RandomUtils.getRandomSymbolString(12));
    System.out.println("result=" + RandomUtils.getRandomSymbolString(12));
    System.out.println("result=" + RandomUtils.getRandomSymbolString(12));
    System.out.println("result=" + RandomUtils.getRandomSymbolString(12));
  }

  @Test
  public void randomOut() throws Exception {
    for (int i = 0; i < 12; i++) {
      System.out.println("date= "+RandomUtils.getRandomChinese(6));
    }
    System.out.println("end========");

    for (int i = 0; i < 12; i++) {
      System.out.println("date= "+ RandomUtils.getRandomChineseSimple(6));
    }
    System.out.println("end========");

    for (int i = 0; i < 12; i++) {
      System.out.println("data="+ RandomUtils.getRandomNumberString(1));
    }
    System.out.println("end========");
  }



  @Test
  public void intToChar() throws Exception {
    for (int i = 0; i < 128; i++) {
      System.out.println("item:" + i + " = " + (char) i);
    }
    System.out.println("date="+ThreadLocalRandom.current().nextBoolean());
    System.out.println("date="+ThreadLocalRandom.current().nextBoolean());
  }

  @Test
  public void stringTest(){
    //String的构造问题
    String value1="value";
    String value2="value";
    String value3=new String("value");
    String value4=new String("value").intern();
    assertEquals(true,value1==value2);//常量池 机制导致他们都指向相同内存地址
    assertEquals(false,value1==value3);//new 一定创建出新的内存对象
    assertEquals(true,value1==value4);//intern 方法会从常量池中找对象 如果存在直接返回 否则创建一个放入

    //String的拼接问题
    //只有使用引号包含文本的方式创建的String对象之间使用“+”连接产生的新对象才会被加入字符串池中。
    //对于所有包含new方式新建对象（包括null）的“+”连接表达式，它所产生的新对象都不会被加入字符串池中
    String value5="value"+"value";
    String value6=value1+value1;
    String value7=new String("value")+new String("value");
    String value8=value1+value1;
    String value9="value"+"value";
    assertEquals(false,value5==value6);//
    assertEquals(false,value5==value7);//不同的拼接方式 指向不同的内存地址
    assertEquals(true,value5==value9);//都是使用引号构建的String拼接 放入了常量池中 指向相同内存地址

    assertEquals(false,value6==value7);
    assertEquals(false,value6==value8);
  }

  @Test
  public void IntegerTest(){
    Integer value1=127;
    Integer value2=127;
    assertEquals(true,value1==value2);

    Integer i1 = 40;
    Integer i2 = 40;
    Integer i3 = 0;
    Integer i4 = new Integer(40);
    Integer i5 = new Integer(40);
    Integer i6 = new Integer(0);

    System.out.println("i1=i2\t" + (i1 == i2));
    System.out.println("i1=i2+i3\t" + (i1 == i2 + i3));
    System.out.println("i4=i5\t" + (i4 == i5));
    System.out.println("i4=i5+i6\t" + (i4 == i5 + i6));
  }

  @Test
  public void mockStud() throws Exception {
    //You can mock concrete classes, not only interfaces
    // 你可以mock具体的类型,不仅只是接口
    LinkedList<String> mockedList = mock(LinkedList.class);

    //stubbing
    // 测试桩
    when(mockedList.get(0)).thenReturn("first");
//    when(mockedList.get(1)).thenThrow(new RuntimeException());

    //following prints "first"
    // 输出“first”
    System.out.println(mockedList.get(0));

    //following throws runtime exception
    // 抛出异常
//    System.out.println(mockedList.get(1));

    //following prints "null" because get(999) was not stubbed
    // 因为get(999) 没有打桩，因此输出null
    System.out.println(mockedList.get(999));

    //Although it is possible to verify a stubbed invocation, usually it's just redundant
    //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
    //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
    // 验证get(0)被调用的次数
    verify(mockedList).get(0);
  }


  @Test
  public void DataTest() {
    Calendar calendar = Calendar.getInstance();
    System.out.println(calendar.toString());
    System.out.println(calendar.getTime());
  }

  /**
   * java 方法参数传递
   * 基本类型：基本类型作为参数传递时，是传递值的拷贝，无论你怎么改变这个拷贝，原值是不会改变的
   * 对象类型：对象作为参数传递时，是把对象在内存中的地址拷贝了一份传给了参数
   * 总的来说，都是拷贝后传递。
   */
  @Test
  public void MethodTest() {
    String data = new String("123");
    operateParameter(data);
    assertEquals(false, data == null);
  }

  public void operateParameter(String data) {
    data = null;
  }


  @Test
  public void InnerClassTest(){

    final String input="123";

    operataObject(new Action() {
      @Override public void listener(String data) {
        System.out.println(data);
      }
    });

    ExampleUnitTest unit=new ExampleUnitTest();
    ExampleUnitTest.InnerClass innerClass=unit.new InnerClass();

  }

  public void operataObject(Action action){
    action.listener("123");
  }

  public interface Action{
    void listener(String data);
  }


  public class InnerClass{
    public ExampleUnitTest getOuterClass(){
      return ExampleUnitTest.this;
    }
  }
}