package com.example.licola.myandroiddemo.generic;

import com.example.licola.myandroiddemo.generic.DataSimple.Apple;
import com.example.licola.myandroiddemo.generic.DataSimple.Fruit;
import com.example.licola.myandroiddemo.generic.DataSimple.Orange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by 李可乐 on 2017/4/21.
 */

public class MethodData {

  static List<Fruit> fruits = new ArrayList<>();
  static List<Apple> apples = new ArrayList<>();
  static List<Orange> oranges = new ArrayList<>();

  static {
    fruits.add(new Fruit());
    apples.add(new Apple());
    oranges.add(new Orange());
  }

  static class Reader<T> {

    T readExact(List<T> list) {
      return list.get(0);
    }
  }

  static class ReaderCovariant<T> {

    T readExact(List<? extends T> list) {
      return list.get(0);
    }
  }

  static <T> void writeExact(List<T> list,T item){
    list.add(item);
  }

  static <T> void writeExactCovariant(List<? super T> list,T item){
    list.add(item);
  }

  static void readTest1() {
    Reader<Fruit> fruitReader = new Reader<>();
    //编译仅能通过 自身类
    fruitReader.readExact(fruits);
    //编译器不会通过 因为方法里面的泛型并不知道 类间继承关系
//    fruitReader.readExact(apples);
//    fruitReader.readExact(oranges);

  }

  static void readTest2() {
    //能够通过编译 通配符 ？的类型是T的子类就行
    ReaderCovariant<Fruit> fruitReader = new ReaderCovariant<>();
    fruitReader.readExact(fruits);
    fruitReader.readExact(apples);
    fruitReader.readExact(oranges);
  }

  static void writeTest1(){
    writeExact(apples,new Apple());
    writeExact(fruits,new Apple());
  }

  static void writeTest2(){
    writeExactCovariant(apples,new Apple());
    writeExactCovariant(fruits,new Apple());
    Collections.copy(fruits,apples);
  }

  public static void main(String[] args) {
//    List<? extends Fruit> fruits = new ArrayList<>();
    //编译不能通过 不管是 自身、子类、父类 都不能通过编译
//    fruits.add(new Fruit());
//    fruits.add(new Apple());
//    fruits.add(new Orange());
//    fruits.add(new Object());

    readTest1();
    readTest2();

    writeTest1();
    writeTest2();


  }

}
