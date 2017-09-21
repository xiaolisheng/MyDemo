package indi.lisen.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import indi.lisen.db.DynamicDataSource;

@Aspect
@Component
public class DBAspects {
	@Pointcut("execution(public * indi.lisen.service.*Service.*(..))")
	private void everyMethod() {
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	static int count = 0;

	// 之前记录
	@Before("everyMethod()")
	public void before() {
		if (count % 2 == 0) {
			DynamicDataSource.setDBType("dataSource1");
		} else {
			DynamicDataSource.setDBType("dataSource2");
		}
		logger.info("change datasource:" + (count % 2 + 1));
		count++;
	}

}
