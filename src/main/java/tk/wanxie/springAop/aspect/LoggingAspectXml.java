package tk.wanxie.springAop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class LoggingAspectXml {                 // same as LoggingAspect, but use xml instead of annotation

    public Object myAroundAdvice(ProceedingJoinPoint joinPoint) {
        Object object = null;
        try {
            System.out.println("<------ an xml aspect --------");
            object = joinPoint.proceed();
            System.out.println("------ close xml aspect -------->");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }

    public void afterReturnPointCutAdvice(String name, Object returnArg) {
        System.out.println("XML Aspect { Input: " + name + ", Return: " + returnArg + "}");
    }
}
