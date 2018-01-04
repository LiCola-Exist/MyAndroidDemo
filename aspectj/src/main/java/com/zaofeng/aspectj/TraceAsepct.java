package com.zaofeng.aspectj;

import android.util.Log;
import com.example.Wrapper.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by 李可乐 on 2017/4/13.
 */

@Aspect
public class TraceAsepct {


  @Pointcut("within(@com.zaofeng.aspectj.DebugTrace *)")
  public void withinAnnotatedClass() {
  }

  @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
  public void methodInsideAnnotatedType() {
  }

  @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
  public void constructorInsideAnnotatedType() {
  }

  @Pointcut("execution(@com.zaofeng.aspectj.DebugTrace * *(..)) || methodInsideAnnotatedType()")
  public void method() {
  }

  @Pointcut("execution(@com.zaofeng.aspectj.DebugTrace *.new(..)) || constructorInsideAnnotatedType()")
  public void constructor() {
  }

  public static class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
      this.component = component;
    }

    @Override
    public void method(String date) {
      Log.d("Demo", "注解插入代码2");
      component.method(date);
    }
  }


  @Around("method() || constructor()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Decorator decorator = new Decorator((Component) joinPoint.getArgs()[0]);
    Object[] objects = new Object[]{decorator};
    Object result = joinPoint.proceed(objects);
//    Object result = joinPoint.proceed();
    stopWatch.stop();

    DebugLog.log(buildLogMessage(className, methodName, stopWatch.getTotalTimeMillis()));

    return result;
  }

  private String buildLogMessage(String className, String methodName, long totalTimeMillis) {
    StringBuilder message = new StringBuilder();
    message.append("ClassName = ").append(className).append(" ");
    message.append("MethodName = ").append(methodName);
    message.append("[");
    message.append("Duration = ").append(totalTimeMillis).append("ms");
    message.append("]");
    return message.toString();
  }
}
