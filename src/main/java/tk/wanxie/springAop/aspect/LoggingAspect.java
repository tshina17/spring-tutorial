package tk.wanxie.springAop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import tk.wanxie.springAop.model.Circle;

@Aspect                                                         // Aspect is a way to inject method before or after other method call
@Component
public class LoggingAspect {

    /*** Before ***/
                                                                // expression inside @Before() is called pointcut
    @Before("execution(public String getName())")               // this method run before the execution of the target method -getName() for all the classes that have this method
    public void LoggingAdvice() {
        System.out.println("Advice run. Get method called.");
    }

    @Before("execution(public String tk.wanxie.springAop..getName())")      // the .. will execute method for all classes within the springAop package and subpackage
    public void printAdvice() {
        System.out.println("Display toString class method.");
    }

    @Before("execution(public * get*())")                   // wildcard, this will execute for all public method start with get*()
    public void printAllAdvice() {
        System.out.println("Get all ***");
    }

    @Before("execution(* get*(..))")                        // (..) wildcard for 0 or more arguments, (*) only one argument or more
    public void printAllWithArgsAdvice() {
        System.out.println("Get all ***");
    }


    /*** After ***/

    @After("args(name)")                                    // advice call after method call
    public void afterPointCutAdvice(String name) {
        System.out.println("Get all after method call.");
    }

    @AfterReturning(pointcut="args(name)", returning="returnArg")                   // advice call after method call returns no error, can also catch the return value
    public void afterReturnPointCutAdvice(String name, Object returnArg) {
        System.out.println("AfterReturning{ Input: " + name + ", Return: " + returnArg + "}");
    }

    @AfterThrowing(pointcut="args(name)", throwing="ex")                            // advice call after method call throw an error, can also catch the exception threw
    public void afterThrowPointCutAdvice(String name, Exception ex) {
        System.out.println("Get all after method throw an error.");
    }


    /*** Around ***/

    @Around("allToString()")
    public Object myAroundAdvice(ProceedingJoinPoint joinPoint) {           // must have the ProceedingJoinPoint as the main arg
        Object object = null;
        try {
            System.out.println("<--------------");
            object = joinPoint.proceed();                                   // this is the execution of the actual method, we can skip it if we want
            System.out.println("-------------->");                          // you can modify the object here
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }


    /*** Pointcut ***/

    @Pointcut("execution(* toString(..))")
    public void allToString() {}                            // this is a dummy method to used as pointcut

    @Before("allToString()")                                // the pointcut defined above
    public void getterAdvice() {
        System.out.println("To String #1.");
    }

    @Before("allToString()")                                // multiple advices for the same pointcut
    public void getterAdvice2() {
        System.out.println("To String #2.");
    }


    /*** Pointcut - within, args, joinpoint ***/

    @Pointcut("within(tk.wanxie.springAop.model..*)")       // apply to all the method within the model package, 'within' take the class name, not method
    public void allCircleMethod() {}


    @Pointcut("args(circle)")                               // take interface/class as arguments
    public void withArgs(Circle circle) {}                  // circle arg must be the same name as in the args(__) pointcut

    @Before("withArgs(circle)")                            // get the args of the method call
    public void usingJoinPoint(JoinPoint joinPoint, Circle circle) {        // joinpoint can get the class of the method call
        System.out.println("Joinpoint: " + circle);
    }


    @Before("allToString() && allCircleMethod()")           // combine pointcuts together
    public void combinePointcut() {
        System.out.println("Combine Pointcut.");
    }


    /*** Custom annotation - Loggable ***/                  // First create the Loggable annotation, and add @Loggable annotation to class methods
    @Before("@annotation(tk.wanxie.springAop.aspect.Loggable)")                        // this method is called when any class methods with @Loggable annotation is called
    public void customAnnotation() {
        System.out.println("Custom annotation loggable called.");
    }
}
