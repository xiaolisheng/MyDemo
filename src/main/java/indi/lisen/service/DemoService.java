package indi.lisen.service;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
	@Resource
	JdbcTemplate jdbcTemplate;

	public String getHello() {
		return "hello world from service!";
	}

	public void dbTest() {
		String str = "insert into users(name) values('name1'),('name2'),('name3')";
		jdbcTemplate.update(str);
	}
}
