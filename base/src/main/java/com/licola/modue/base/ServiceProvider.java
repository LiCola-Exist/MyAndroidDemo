package com.licola.modue.base;

import android.app.Application;
import android.os.Bundle;
import com.licola.llogger.LLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author LiCola
 * @date 2019/4/8
 */
public class ServiceProvider {

  public static final String META_DATA_VALUE = "module";

  public static boolean register(Application context) {

    List<String> targetClassNames = getManifestMetaData(context, META_DATA_VALUE);
    if (targetClassNames == null || targetClassNames.isEmpty()) {
      return false;
    }

    for (String targetClassName : targetClassNames) {
      IModule module = parseModule(targetClassName);
      module.bind(context);
    }

    return true;
  }

  private static IModule parseModule(String className) {

    Class<?> tClass;
    try {
      tClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Unable to find Module implementation", e);
    }

    Object module = null;

    try {
      module = tClass.getDeclaredConstructor().newInstance();
    } catch (IllegalAccessException e) {
      throwInstantiateModuleException(tClass, e);
    } catch (InstantiationException e) {
      throwInstantiateModuleException(tClass, e);
    } catch (InvocationTargetException e) {
      throwInstantiateModuleException(tClass, e);
    } catch (NoSuchMethodException e) {
      throwInstantiateModuleException(tClass, e);
    }

    if (!(module instanceof IModule)) {
      throw new RuntimeException("Expected instanceof Module, but found: " + module);
    }

    return (IModule) module;
  }

  private static void throwInstantiateModuleException(Class<?> clazz, Exception e) {
    throw new RuntimeException("Unable to instantiate GlideModule implementation for " + clazz, e);
  }


  private static List<String> getManifestMetaData(Application context, String metaDataValue) {

    if (context == null) {
      throw new IllegalArgumentException("context can not be null");
    }

    if (metaDataValue == null) {
      throw new IllegalArgumentException("metaDataValue can not be null");
    }

    Bundle metaData = AppUtils.getMetaData(context);
    if (metaData == null) {
      return Collections.emptyList();
    }

    ArrayList<String> targetNames = new ArrayList<>();

    Set<String> keySet = metaData.keySet();
    for (String key : keySet) {
      Object obj = metaData.get(key);
      if (!(obj instanceof String)) {
        continue;
      }

      String value = metaData.getString(key);
      LLogger.d(key, value);

      if (metaDataValue.equals(value)) {
        targetNames.add(key);
      }

    }

    return targetNames;

  }


}
