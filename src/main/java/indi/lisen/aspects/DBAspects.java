package indi.lisen.aspects;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import indi.lisen.util.SpringContextUtil;

@Aspect
@Component
public class DBAspects implements Ordered {
	@Pointcut("execution(public * indi.lisen.service.*Service.*(..))")
	private void everyMethod() {
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	static int count = 0;

	// 之前记录
	// @Before("everyMethod()")
	public void before() throws Exception {
		// 修改MyBatis的数据源
		SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) SpringContextUtil
				.getBean(SqlSessionFactoryBean.class);

		if (count % 2 == 0) {
			sqlSessionFactoryBean.setDataSource((DataSource) SpringContextUtil
					.getBean("atomikosDataSource1"));// 修改mybatis的数据源
		} else {
			sqlSessionFactoryBean.setDataSource((DataSource) SpringContextUtil
					.getBean("atomikosDataSource2"));// 修改mybatis的数据源
		}
		logger.info(
				"change datasource to atomikosDataSource" + (count % 2 + 1));
		count++;
	}

	// 设置为最先执行
	@Override
	public int getOrder() {
		return 2;
	}

}
