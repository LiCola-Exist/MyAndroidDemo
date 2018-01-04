package com.example.licola.myandroiddemo.java.proxy;

import java.util.List;
import java.util.Map;

/**
 * Created by 李可乐 on 2017/5/19.
 * 订单接口 定义行为
 */

public interface OrderApi {

  /**
   * 获取产品名称
   * @return
   */
  String getProductName();

  /**
   * 设置 订单订购的产品 名称
   * @param productName
   * @param user
   */
  void setProductName(String productName, String user);

  /**
   * 获取订单数量
   * @return
   */
  int getOrderNum();

  /**
   * 设置订单订购的数量
   * @param orderNum
   * @param user
   */
  void setOrderNum(int orderNum, String user);

  /**
   * 获取创建订单的人员
   * @return
   */
  String getOrderUser();

  /**
   * 设置创建订单的人员
   * @param orderUser
   * @param user
   */
  void setOrderUser(String orderUser, String user);

  Map<String,Integer> getMapData(String name,List<String> data);
}
