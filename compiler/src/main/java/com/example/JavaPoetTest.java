package com.example;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.IOException;
import javax.lang.model.element.Modifier;

/**
 * Created by LiCola on 2017/7/4.
 */

public class JavaPoetTest {

  public static  void main(String[] args){
    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, "args")
        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
        .build();

    File file=new File("./");
    try {
      javaFile.writeTo(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
