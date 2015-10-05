package api.aspect;

import api.util.ApiLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Component
@Aspect
public class DebugAspect
{
    @Autowired private ApiLogger logger;

    @Pointcut("execution(* api..*(..))")
    private void anyApiPackageMethod() {}

    @Pointcut("execution(* api.util.ApiLogger.*(..))")
    private void anyLoggingMethodCall() {}

    @Before("anyApiPackageMethod() && !anyLoggingMethodCall()")
    public void printMethod(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().toString());
    }
}
