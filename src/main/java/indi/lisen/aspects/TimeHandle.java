package indi.lisen.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeHandle {

	long start;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(public * indi.lisen.service.*Service.*(..))")
	private void everyMethod() {
	}

	// 之前记录
	@Before("everyMethod()")
	public void startTime() {
		start = System.currentTimeMillis();
	}

	// 之后结果
	@After("everyMethod()")
	public void endTime() {
		logger.info("本次时间：" + (System.currentTimeMillis() - start) + "ms");
	}
}
