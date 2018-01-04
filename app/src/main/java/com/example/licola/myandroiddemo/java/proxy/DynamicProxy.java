package com.example.licola.myandroiddemo.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 李可乐 on 2017/5/19.
 */

public class DynamicProxy implements InvocationHandler {

  /**
   * 被代理对象
   */
  /**
   * 获取绑定好代理和具体目标对象后的目标对象接口
   *
   * @param tClass 具体的订单对象
   * @return 绑定好的目标对象接口
   */
  @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
  public <T> T getProxyOrderApi(final Class<T> tClass) {
    //把被代理对象 和动态代理关联
    T orderApi = (T) Proxy.newProxyInstance(
        tClass.getClassLoader(),
        new Class<?>[]{tClass},
        this
    );
    return orderApi;
  }

//  public void setOrderApi(OrderApi orderApi) {
//    //设置被代理对象
//    this.orderApi = orderApi;
//  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("result="+"动态代理返回值 "+" Method="+method.getReturnType().getName());
    return "动态代理返回值";

//    //根据方法名 判断是否需要权限控制
//    if (method.getName().startsWith("set")) {
//      //如果不是创建人，那就不能修改
//      if (orderApi.getOrderUser() != null
//          && orderApi.getOrderUser().equals(args[1])) {
//        //可以操作
//        System.out.println( args[1]
//            + "成功通过代理修改数据");
//        return method.invoke(orderApi, args);
//      } else {
//        System.out.println("对不起，" + args[1]
//            + "，您无权修改本订单中的数据");
//      }
//    } else {
//      //普通方法 直接调用被代理对象
//      return method.invoke(orderApi, args);
//    }
//    return null;
  }
}
