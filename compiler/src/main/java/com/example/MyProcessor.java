package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyProcessor extends AbstractProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(MyAnnotation.class.getCanonicalName());
        return Collections.singleton(MyAnnotation.class.getCanonicalName());
    }

    /**
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ArrayList<String> atyList = new ArrayList<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MyAnnotation.class)) {
            atyList.add(element.getSimpleName().toString());
        }

        // start builder interface
        TypeSpec.Builder interfaceIntentBuilder = TypeSpec.interfaceBuilder("IntentContract");

        AnnotationSpec.Builder interfaceAnnotationIntDefBuilder = AnnotationSpec.builder(ClassName.get("android.support.annotation", "IntDef"));


        String itemAty;
        for (int i = 0, size = atyList.size(); i < size; i++) {
            itemAty = atyList.get(i);
            FieldSpec interfaceField = FieldSpec.builder(int.class, itemAty)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$L", i)
                    .build();
            interfaceIntentBuilder.addField(interfaceField);
            interfaceAnnotationIntDefBuilder.addMember("value", "$L", itemAty);
        }


        AnnotationSpec interfaceAnnotationRetention = AnnotationSpec.builder(Retention.class)
                .addMember("value", "$L", RetentionPolicy.SOURCE)
                .build();


        TypeSpec annotationIntent = TypeSpec.annotationBuilder("IntentType")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(interfaceAnnotationIntDefBuilder.build())
                .addAnnotation(interfaceAnnotationRetention)
                .build();


        interfaceIntentBuilder
                .addModifiers(Modifier.PUBLIC)
                .addType(annotationIntent);

        JavaFile javaFileAnnotation = JavaFile.builder("com.example.IntentContract", interfaceIntentBuilder.build())
                .addStaticImport(RetentionPolicy.SOURCE)
                .build();

        try {
            javaFileAnnotation.writeTo(processingEnv.getFiler());
            javaFileAnnotation.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ClassName annotationClassName = ClassName.get("com.example.IntentContract", "IntentContract", "IntentType");
        ParameterSpec target = ParameterSpec.builder(int.class, "target")
                .addModifiers(Modifier.FINAL)
                .addAnnotation(annotationClassName)
                .build();

        MethodSpec getClassMethod = MethodSpec.methodBuilder("getIntentClass")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(target)
                .addStatement(" return classHashMap.get(target) != null ? classHashMap.get(target) : null")
                .returns(Class.class)
                .build();

        ClassName classNameAty = ClassName.get("com.example.licola.myandroiddemo", atyList.get(0));
        ClassName classNameContract = ClassName.get("com.example.IntentContract", "IntentContract");

        CodeBlock codeBlock = CodeBlock.builder().add("classHashMap.put($L.$L,$L.class);\n ", classNameContract.simpleName(), atyList.get(0), classNameAty).build();

        TypeSpec classIntentManager = TypeSpec.classBuilder("IntentManager")
                .addModifiers(Modifier.PUBLIC)
                .addField(ParameterizedTypeName.get(HashMap.class, Integer.class, Class.class), "classHashMap", Modifier.PRIVATE, Modifier.STATIC)
                .addStaticBlock(codeBlock)
                .addMethod(getClassMethod)
                .build();

        JavaFile javaFileManager = JavaFile.builder("com.example.IntentContract", classIntentManager)
                .build();
        try {
            javaFileManager.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
