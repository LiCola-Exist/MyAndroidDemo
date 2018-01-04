package com.example.licola.myandroiddemo;

import com.example.licola.myandroiddemo.reflection.ReflectionModel;
import com.example.licola.myandroiddemo.reflection.ReflectionSubModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/4/18.
 */

public class ClassReflection {

  private static final String ClassName = "com.example.licola.myandroiddemo.reflection.ReflectionModel";
  private static final String ClassSubName = "com.example.licola.myandroiddemo.reflection.ReflectionSubModel";


  public static String mapperArrayToString(Class<?>[] classes) {
    StringBuilder build = new StringBuilder();
    for (Class<?> item :
        classes) {
      build.append("item=").append(item.getName()).append(" ");
    }

    return build.toString();
  }

  public static String mapperArrayToString(Annotation[] list) {
    StringBuilder build = new StringBuilder();
    for (Annotation item :
        list) {
      build.append("item=").append(item.annotationType().toString()).append(" ");
    }

    return build.toString();
  }

  @Test
  public void ClassReflectionTest() throws Exception {
    System.out.println("1--->");
    Reflect1();

    System.out.println("2--->");
    Reflect2();

    System.out.println("3--->");
    Reflect3();

    System.out.println("4--->");
    Reflect4();

    System.out.println("5--->");
    Reflect5();

    System.out.println("6--->");
    Reflect6();

    System.out.println("7--->");
    Reflect7();
  }


  /**
   * 反射调用 方法
   */
  @Test
  public void Reflect7()
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
    Class<?> class1 = null;
    class1 = Class.forName(ClassSubName);
    Method methodNot = class1.getMethod("doingMethod");
    methodNot.invoke(class1.newInstance());

    Method methodHas = class1.getMethod("action", boolean.class);
    methodHas.invoke(class1.newInstance(), true);

  }

  /**
   * 读取类的信息 父类 域 方法 实现的接口等
   */
  @Test
  public void Reflect6() throws ClassNotFoundException {
    Class<?> class1 = null;
    Class<?> classA = ReflectionSubModel.TestAnnotations.class;
    class1 = Class.forName(ClassSubName);

    Constructor<?>[] constructors = class1.getConstructors();
    for (Constructor itemConstructor :
        constructors) {
      System.out.println(itemConstructor.getName());
    }


    Class<?> superClass = class1.getSuperclass();
    System.out.println("SuperClass name=" + superClass.getName());
    System.out.println("==================");

    Field[] fields = class1.getDeclaredFields();//得到所有声明的域 但是不包含继承的域
    for (Field item :
        fields) {
      System.out.println("Field item=" + item);
    }
    System.out.println("以上得到所有声明的域 但是不包含继承的域==================");

    Field[] fields1 = class1.getFields();//得到所有 public声明的变量 包括继承域
    for (Field item :
        fields1) {
      System.out.println("Field item=" + item);
      int mod = item.getModifiers();
      System.out.println(Modifier.toString(mod));
    }
    System.out.println("以上得到所有public声明的变量 包括继承域==================");

    Method[] methods = class1.getDeclaredMethods();
    for (Method item :
        methods) {

      System.out.println("method  " + item);
      System.out.println("method 方法名 " + item.getName());
      System.out.println("method 入参 " + mapperArrayToString(item.getParameterTypes()));
      System.out.println("method 注解 " + mapperArrayToString(item.getAnnotations()));
      System.out.println("method 返回类型 " + item.getReturnType());
      System.out.println("method 修饰符 " + Modifier.toString(item.getModifiers()));
      System.out.println("item method end==================");
    }
    System.out.println("以上得到所有声明的方法 但是不包含继承的方法==================");

    Class<?>[] interfaces = class1.getInterfaces();
    for (Class<?> item :
        interfaces) {
      System.out.println("实现的接口 " + item.getName());
    }
    System.out.println("以上得到实现的接口==================");
  }

  /**
   * 反射得到类 并操作它的成员变量
   */
  @Test
  public void Reflect5()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
    Class<? extends ReflectionModel> class1 = null;
    class1 = (Class<? extends ReflectionModel>) Class.forName(ClassName);
    Object object = class1.newInstance();
    Field modelField = class1.getDeclaredField("name");
    modelField.setAccessible(true);
    modelField.set(object, "set name from getDeclaredField");

    System.out.println("Model get field=" + modelField.get(object));
  }

  /**
   * 从类名 得到构造方法数组 并调用无参和有参构造方法
   */
  @Test
  public void Reflect4()
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class<?> class1 = null;
    ReflectionModel model1 = null;
    ReflectionModel model2 = null;

    class1 = Class.forName(ClassName);
    Constructor<?>[] constructors = class1.getConstructors();//得到构造方法 数组

    for (Constructor<?> item : constructors) {
      System.out.println("Constructor item" + item);
    }

    model1 = (ReflectionModel) constructors[0].newInstance();
    model1.setName("reflect constructors set name");
    model1.setRank(1);

    model2 = (ReflectionModel) constructors[1].newInstance("reflect constructors name", 2);

    System.out.println("Model1 " + model1.toString());
    System.out.println("Model2 " + model2.toString());
  }

  @Test
  public void Reflect3()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class<?> class1 = null;
    class1 = Class.forName(ClassName);
    ReflectionModel model = (ReflectionModel) class1.newInstance();//使用无参构造
    model.setName("reflect set name");
    model.setRank(0);
    System.out.println("Model value: model=" + model.toString());
  }

  /**
   * 根据路径和类 得到包名和类名
   */
  @Test
  public void Reflect2() throws ClassNotFoundException {
    Class<?> class1 = null;
    Class<?> class2 = null;

    class1 = Class.forName(ClassName);
    System.out.println(
        "Reflect2 pack name=" + class1.getPackage().getName() + " Class name=" + class1.getName());

    class2 = ReflectionModel.class;
    System.out.println(
        "Reflect2 pack name=" + class2.getPackage().getName() + " Class name=" + class2.getName());

  }


  /**
   * 根据对象读取包名类名
   */
  @Test
  public void Reflect1() {
    ReflectionModel model = new ReflectionModel();
    System.out.println(
        "Reflect1 pack name=" + model.getClass().getPackage().getName());
    System.out.println("Reflect1 class name=" + model
        .getClass().getName());

  }


}
