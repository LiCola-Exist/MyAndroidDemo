package com.example.licola.myandroiddemo.reflection;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 李可乐 on 2017/4/18.
 */

public class ReflectionSubModel extends ReflectionModel implements ActionInterface {

  public static final int Action1 = 1;
  public static final int Action2 = 2;

  public static final long[] Actions = {Action1, Action2};

  @IntDef({1, 2})
  @Retention(RetentionPolicy.CLASS)
  public @interface TestAnnotations {

  }


  private String subName;

  public String publicName;
  protected String protectedName;
  String packName;
  @TestAnnotations
  public int annotationsValue;

  public ReflectionSubModel() {

  }
  public ReflectionSubModel(String subName, String publicName) {
    this.subName = subName;
    this.publicName = publicName;
  }

  public String getSubName() {
    return subName;
  }

  public void setSubName(String subName) {
    this.subName = subName;
  }

  private void privateMethod() {

  }

  @VisibleForTesting
  public void annotationMethod(@Nullable String date) {

  }

  public void setAnnotationsValue(@TestAnnotations int annotationsValue) {
    this.annotationsValue = annotationsValue;
  }

  public void doingMethod() {
    System.out.println("调用public无参方法");
  }

  @Override
  public void action(boolean isAction) {
    System.out.println("调用public接口实现，有参方法");
  }
}
