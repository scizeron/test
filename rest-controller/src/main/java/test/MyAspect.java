package test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
//@Component
public class MyAspect {
  
  @Around("@annotation(requestMapping)")
  public Object around(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
    return pjp.proceed();
  }

}
