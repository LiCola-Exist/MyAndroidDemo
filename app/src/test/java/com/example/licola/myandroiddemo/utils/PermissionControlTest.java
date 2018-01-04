package com.example.licola.myandroiddemo.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by 李可乐 on 2017/5/15.
 */
public class PermissionControlTest {

  @Test
  public void setPermission() throws Exception {
    PermissionControl permissionControl = new PermissionControl();
    permissionControl.enable(PermissionControl.ALLOW_SELECT | PermissionControl.ALLOW_DELETE);
    assertEquals(true,permissionControl.isAllow(PermissionControl.ALLOW_SELECT));
    assertEquals(false,permissionControl.isAllow(PermissionControl.ALLOW_UPDATE));

    permissionControl.disable(PermissionControl.ALLOW_DELETE);
    assertEquals(false,permissionControl.isAllow(PermissionControl.ALLOW_DELETE));
  }

}