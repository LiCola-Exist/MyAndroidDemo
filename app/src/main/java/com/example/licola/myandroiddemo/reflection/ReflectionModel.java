package com.example.licola.myandroiddemo.reflection;

/**
 * Created by 李可乐 on 2017/4/18.
 */

public class ReflectionModel {

  private String name;
  private int rank;

  public String mPublicName;
  protected String mProtectedName;
  String mDefaulPackName;

  public ReflectionModel() {
  }

  public ReflectionModel(String name, int rank) {
    this.name = name;
    this.rank = rank;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  @Override
  public String toString() {
    return "ReflectionModel{" +
        "name='" + name + '\'' +
        ", rank=" + rank +
        '}';
  }
}
