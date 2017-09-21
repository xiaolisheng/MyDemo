package indi.lisen.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static Logger logger = LoggerFactory
			.getLogger(DynamicDataSource.class);
	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("当前线程数据源:{}", contextHolder.get());
		return contextHolder.get();
	}

	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}

	public static void clearDBType() {
		contextHolder.remove();
	}

}
