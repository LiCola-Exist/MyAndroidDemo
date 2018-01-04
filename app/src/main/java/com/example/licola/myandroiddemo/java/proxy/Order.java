package com.example.licola.myandroiddemo.java.proxy;

import java.util.List;
import java.util.Map;

/**
 * Created by 李可乐 on 2017/5/19.
 * 订单对象 不包访问控制
 */

public class Order implements OrderApi {
  /**
   * 订单订购的产品名称
   */
  private String productName;
  /**
   * 订单订购的数量
   */
  private int orderNum;
  /**
   * 创建订单的人员
   */
  private String orderUser;

  public Order(String productName, int orderNum, String orderUser) {
    this.productName = productName;
    this.orderNum = orderNum;
    this.orderUser = orderUser;
  }

  @Override
  public String getProductName() {
    return productName;
  }

  @Override
  public void setProductName(String productName, String user) {
    this.productName=productName;
  }

  @Override
  public int getOrderNum() {
    return orderNum;
  }

  @Override
  public void setOrderNum(int orderNum, String user) {
    this.orderNum=orderNum;
  }

  @Override
  public String getOrderUser() {
    return orderUser;
  }

  @Override
  public void setOrderUser(String orderUser, String user) {
    this.orderUser=orderUser;
  }

  @Override
  public Map<String, Integer> getMapData(String name, List<String> data) {
    return null;
  }


}
