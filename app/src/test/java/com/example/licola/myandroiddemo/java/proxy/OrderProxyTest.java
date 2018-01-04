package com.example.licola.myandroiddemo.java.proxy;

import java.util.ArrayList;
import java.util.Map;
import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/19.
 */
public class OrderProxyTest {

  @Test
  public void staticProxy() throws Exception {
    /**
     * 创建一个被具有控制权限代理订单对象
     */
    OrderApi order = new OrderProxy(new Order("静态代理", 10, "Vivian"));
    order.setOrderNum(100, "Lee");
    order.setOrderNum(1, "Vivian");
  }

  @Test
  public void dynamicProxy() throws Exception {
    //创建被代理对象
    Order order = new Order("动态代理", 10, "Lee");
    //创建动态代理
    DynamicProxy dynamicProxy = new DynamicProxy();
    //把订单对象和动态代理关联
    OrderApi orderApi = dynamicProxy.getProxyOrderApi(OrderApi.class);
    System.out.println("getOrderUser= "+orderApi.getOrderUser());
//     orderApi.setOrderNum(12, "Vivian");
//    orderApi.setOrderNum(1, "Lee");
  }

}