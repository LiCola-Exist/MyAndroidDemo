package com.example.licola.myandroiddemo.utils;

import com.example.licola.myandroiddemo.Model.ViewWrapModel;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.junit.Test;

/**
 * Created by LiCola on 2017/6/9.
 */

public class ReferenceTest {

  @Test
  public void all(){
    ViewWrapModel model=new ViewWrapModel();

    WeakReference<ViewWrapModel> reference=new WeakReference<ViewWrapModel>(model);

    model=null;

    System.gc();

    ViewWrapModel wrapModel=reference.get();
    if (wrapModel==null){
      System.out.println("reference ==null");
    }else {
      System.out.println("reference !=null");
    }
  }
}
