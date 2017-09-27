package indi.lisen;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class Main {
	static String[] names = {"song of ice and fire", "i am a bad bad boy",
			"sunny", "go to hell", "five"};
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	static int count = 0;

	static JdbcTemplate jdbcTemplate;

	static synchronized void count(int val) {
		count += val;
		logger.info(count + "");
	}

	static void init() throws IOException {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(
					Resources.getResourceAsFile("logback-config.xml"));
		} catch (JoranException e) {
			e.printStackTrace();
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		System.out.println("===================");
		logger.info("Hello {}", "debug message");
	}

	public static void main(String[] args) throws Exception {
		init();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-config.xml");
		jdbcTemplate = context.getBean(JdbcTemplate.class);
		long start = System.currentTimeMillis();

		ExecutorService es = Executors.newFixedThreadPool(10);
		StringBuilder sb = null;
		Random ra = new Random();
		for (int j = 0; j < 1000; j++) {
			sb = new StringBuilder("insert into book(name,type,aid) values");
			for (int i = 0; i < 10000; i++) {
				sb.append("('").append(names[ra.nextInt(5)]).append("',")
						.append(ra.nextInt(1000)).append(",")
						.append((j * 10000) + i + 10000000).append("),");
			}
			System.out.println(sb.length());
			es.execute(new Task(sb));
		}

		es.shutdown();
		try {
			// awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
			while (!es.awaitTermination(1, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("花了:" + (end - start) + "ms");
	}

	static class Task implements Runnable {

		StringBuilder sb = null;

		public Task(StringBuilder sb) {
			this.sb = sb;
		}

		@Override
		public void run() {
			int ret = jdbcTemplate
					.update(sb.deleteCharAt(sb.length() - 1).toString());
			count(ret);
		}

	}
}
