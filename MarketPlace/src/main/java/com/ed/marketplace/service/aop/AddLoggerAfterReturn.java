package com.ed.marketplace.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AddLoggerAfterReturn {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @After("execution(* com.ed.marketplace.service.CustomerService(..))")
    public void afterReturning(JoinPoint returnValue) {
        logger.info(String.format("Value return was - %s", returnValue));
    }
}
