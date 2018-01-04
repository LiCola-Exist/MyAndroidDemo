package com.example.licola.myandroiddemo.utils;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 李可乐 on 2017/5/15.
 * 使用int+逻辑与或非 替代boolean控制 保存多个状态值
 * 理论上int每一位都可以表示一个布尔值,即32个布尔值
 * 且内存模型上，
 *
 */

public class PermissionControl {
  public static final int ALLOW_SELECT = 1 << 0;//0001
  public static final int ALLOW_INSERT = 1 << 1;//0010
  public static final int ALLOW_UPDATE = 1 << 2;//0100
  public static final int ALLOW_DELETE = 1 << 3;//1000

  private int flag;//保存状态的int值

  @IntDef({ALLOW_SELECT, ALLOW_INSERT, ALLOW_UPDATE, ALLOW_DELETE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface Permission {

  }

  //设置权限
  public void setPermission(@Permission int permission) {
    this.flag = permission;
  }

  //添加权限
  public void enable(int permission) {
    flag |= permission;
  }

  //禁用权限
  public void disable(int permission) {
    flag &= ~permission;
  }

  /**
   * 是否拥有某些权限
   */
  public boolean isAllow(int permission) {
    return (flag & permission) == permission;
  }

  /**
   * 是否禁用某些权限
   */
  public boolean isNotAllow(int permission) {
    return (flag & permission) == 0;
  }


}
