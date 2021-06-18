package com.examples.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationAspect {
	@Before("execution(* PersonService.getPerson(..))") // point-cut expression
	public void logBeforeV1(JoinPoint joinPoint) {
		System.out.println("EmployeeCRUDAspect.logBeforeV1() : " + joinPoint.getSignature().getName());
	}
}
