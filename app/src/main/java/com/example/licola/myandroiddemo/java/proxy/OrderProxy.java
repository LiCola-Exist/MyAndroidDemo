package com.example.licola.myandroiddemo.java.proxy;

import java.util.List;
import java.util.Map;

/**
 * Created by 李可乐 on 2017/5/19.
 */

public class OrderProxy implements OrderApi {

  /**
   * 持有被代理的目标对象
   */
  Order order = null;

  /**
   * 构造方法 得到具体的被代理对象
   */
  public OrderProxy(Order subjectOrder) {
    this.order = subjectOrder;
  }


  @Override
  public void setProductName(String productName, String user) {
    //控制访问权限，只有创建订单的人员才能够修改
    if (user != null && user.equals(this.getOrderUser())) {
      System.out.println(user
          + "修改订单中的产品名称");
      order.setProductName(productName, user);
    } else {
      System.out.println("对不起" + user
          + "，您无权修改订单中的产品名称。");
    }
  }

  @Override
  public void setOrderNum(int orderNum, String user) {
    //控制访问权限，只有创建订单的人员才能够修改
    if (user != null && user.equals(this.getOrderUser())) {
      System.out.println(user
          + "修改订单中的订购数量");
      order.setOrderNum(orderNum, user);
    } else {
      System.out.println("对不起" + user
          + "，您无权修改订单中的订购数量。");
    }
  }

  @Override
  public void setOrderUser(String orderUser, String user) {
    //控制访问权限，只有创建订单的人员才能够修改
    if (user != null && user.equals(this.getOrderUser())) {
      System.out.println(user
          + "修改订单中的订购人");
      order.setOrderUser(orderUser, user);
    } else {
      System.out.println("对不起" + user
          + "，您无权修改订单中的订购人。");
    }
  }

  @Override
  public Map<String, Integer> getMapData(String name, List<String> data) {
    return null;
  }




  /*访问方法 不加权限控制 直接访问代理对象*/
  @Override
  public String getProductName() {
    return order.getProductName();
  }

  @Override
  public int getOrderNum() {
    return order.getOrderNum();
  }

  @Override
  public String getOrderUser() {
    return order.getOrderUser();
  }
}
