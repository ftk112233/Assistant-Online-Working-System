package com.jzy.manager.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @ClassName CommonLogger
 * @Author JinZhiyun
 * @Description 通用日志管理
 * @Date 2019/5/21 10:13
 * @Version 1.0
 **/
@Aspect
@Component
public class CommonLogger {
    private final static Logger logger = LogManager.getLogger(CommonLogger.class);

    private static final long METHOD_TIME_THRESHOLD = 1000;

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.someapp.web package or any sub-package
     * under that.
     */
    @Pointcut("within(com.jzy.web..*)")
    public void inWebLayer() {}

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.someapp.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.jzy.service..*)")
    public void inServiceLayer() {}

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.someapp.dao package or any sub-package
     * under that.
     */
    @Pointcut("within(com.jzy.dao..*)")
    public void inDataAccessLayer() {}

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     *
     * If you group service interfaces by functional area (for example,
     * in packages com.xyz.someapp.abc.service and com.xyz.someapp.def.service) then
     * the pointcut expression "execution(* com.xyz.someapp..service.*.*(..))"
     * could be used instead.
     *
     * Alternatively, you can write the expression using the 'bean'
     * PCD, like so "bean(*Service)". (This assumes that you have
     * named your Spring service beans in a consistent fashion.)
     */
    @Pointcut("execution(* com.jzy.service.*.*(..))")
    public void businessService() {}

    /**
     * A data access operation is the execution of any method defined on a
     * dao interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* com.jzy.dao.*.*(..))")
    public void dataAccessOperation() {}

    @Pointcut("execution(* com.jzy.web.controller.*.*(..))")
    public void controllerRequests() {}

    /**
     * dao方法耗时aop实现
     *
     * @param pjp
     * @return
     */
    @Around("dataAccessOperation()")
    public Object daoTimeTestAround(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        if (spendTime > METHOD_TIME_THRESHOLD) {
            logger.info(methodName + "dao方法耗时严重：" + (endTime - startTime) + "ms");
        } else {
            logger.debug(methodName + "dao方法耗时正常：" + (endTime - startTime) + "ms");
        }
        return obj;
    }


    /**
     * 业务方法耗时aop实现
     *
     * @param pjp
     * @return
     */
    @Around("businessService()")
    public Object serviceTimeTestAround(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        if (spendTime > METHOD_TIME_THRESHOLD) {
            logger.warn(methodName + "业务方法耗时严重：" + (endTime - startTime) + "ms");
        } else {
            logger.debug(methodName + "业务方法耗时正常：" + (endTime - startTime) + "ms");
        }
        return obj;
    }

    /**
     * controller方法耗时aop实现
     * @param pjp
     * @return
     */
    @Around("controllerRequests()")
    public Object controllerTimeTestAround(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        if (spendTime > METHOD_TIME_THRESHOLD) {
            logger.info(methodName + "controller请求方法耗时严重：" + (endTime - startTime) + "ms");
        } else {
            logger.debug(methodName + "controller请求方法耗时正常：" + (endTime - startTime) + "ms");
        }
        return obj;
    }
}
