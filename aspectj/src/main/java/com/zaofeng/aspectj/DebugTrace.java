package com.zaofeng.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 李可乐 on 2017/4/13.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface DebugTrace {

}
