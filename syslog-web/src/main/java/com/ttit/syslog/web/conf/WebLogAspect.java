package com.ttit.syslog.web.conf;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Description: 统一的访问日志
 *
 * @author 小谢
 * Date: 2019/5/2117:06
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * com.ttit.syslog.web.controller..*.*(..))")
    public void logPoint() {
    }

    private ThreadLocal<Long> startTime;

    @Before("logPoint()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime = new ThreadLocal<>();
        startTime.set(System.nanoTime());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("====================================================================");
        log.info("URL : " + request.getRequestURL().toString() + " " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "logPoint()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("SPEND TIME : " + TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - startTime.get())) + "ms");
        log.info("====================================================================");
        if (startTime != null) {
            startTime.remove();
        }
    }

}
