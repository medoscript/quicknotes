package com.example.quicknotes.logging;


import com.example.quicknotes.domain.dto.TaskDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//
import java.util.List;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* com.example.quicknotes.service.TaskManagerService.save(..))")
    public void saveTask() {}

    @Before("saveTask()")
    public void beforeSavingTask(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Method save of the class TaskManagerService was called with parameter {}", args[0]);
    }

    @After("saveTask()")
    public void afterSavingTask() {
        logger.info("Method save of the class TaskManagerService finished its work");
    }

    @Pointcut("execution(* com.example.quicknotes.service.TaskManagerService.getById(..))")
    public void getTaskById() {}

    @AfterReturning(pointcut = "getTaskById()", returning = "result")
    public void afterReturningTaskById(Object result) {
        logger.info("Method getById of the class TaskManagerService successfully returned result: {}", result);
    }

    @AfterThrowing(pointcut = "getTaskById()", throwing = "e")
    public void afterThrowingAnExceptionWhileGettingTaskById(Exception e) {
        logger.info("Method getById of the class TaskManagerService threw an exception: {}", e.getMessage());
    }

    @Pointcut("execution(* com.example.quicknotes.service.TaskManagerService.getAll(..))")
    public void getAllTasks() {}

    @Around("getAllTasks()")
    public Object aroundGettingAllTasks(ProceedingJoinPoint joinPoint) {
        logger.info("Method getAll of the class TaskManagerService called");

        List<TaskDto> result = null;

        try {
            result = ((List<TaskDto>) joinPoint.proceed())
                    .stream()
                    .toList();
        } catch (Throwable e) {
            logger.error("Method getAll of the class TaskManagerService threw an exception: {}", e.getMessage());
        }

        logger.info("Method getAll of the class TaskManagerService finished its work");
        return result;
    }

    @Pointcut("execution(* com.example.quicknotes.service.TaskManagerService.*(..))")
    public void allMethodsOfTaskService() {}

    @Before("allMethodsOfTaskService()")
    public void beforeAllMethodsOfTaskService(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} of the class TaskManagerService was called", methodName);
    }

    @After("allMethodsOfTaskService()")
    public void afterAllMethodsOfTaskService(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} of the class TaskManagerService finished its work", methodName);
    }

    @Pointcut("execution(* com.example.quicknotes.service.TaskManagerService..*(..))")
    public void allMethodsOfServiceTasks() {}

    @Before("allMethodsOfServiceTasks()")
    public void beforeAllMethodsOfServiceTasks(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info("Method {} of the class {} was called", methodName, className);
    }

    @After("allMethodsOfServiceTasks()")
    public void afterAllMethodsOfServiceTasks(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info("Method {} of the class {} finished its work", methodName, className);
    }

    @AfterReturning(
            pointcut = "allMethodsOfServiceTasks()",
            returning = "result"
    )
    public void afterReturningForServiceTasks(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info("Method {} of the class {} successfully returned result {}", methodName, className, result);
    }

    @AfterThrowing(
            pointcut = "allMethodsOfServiceTasks()",
            throwing = "e"
    )
    public void afterThrowingForServiceTasks(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info("Method {} of the class {} threw an exception: {}", methodName, className, e.getMessage());
    }
}