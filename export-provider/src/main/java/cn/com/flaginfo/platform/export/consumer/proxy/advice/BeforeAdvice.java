package cn.com.flaginfo.platform.export.consumer.proxy.advice;

import java.lang.reflect.Method;

public interface BeforeAdvice {
	
    void before(Object o,Method method,Object [] args);
	
}
