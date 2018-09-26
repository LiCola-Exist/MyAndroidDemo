package com.example.licola.myandroiddemo.java.xml;

/**
 * @author LiCola
 * @date 2018/9/26
 */
public class Student {

  private String name;
  private String sex;
  private String nickName;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", sex='" + sex + '\'' +
        ", nickName='" + nickName + '\'' +
        '}';
  }

}
