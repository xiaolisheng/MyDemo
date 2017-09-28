package indi.lisen.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import indi.lisen.dao.UsersMapper;
import indi.lisen.model.Users;

@Service
public class DemoService {
	@Resource
	JdbcTemplate jdbcTemplate1;

	JdbcTemplate jdbcTemplate;

	@Resource
	JdbcTemplate jdbcTemplate2;
	// @Resource
	UsersMapper usersMapper;

	@Resource
	SqlSessionTemplate sqlSessionTemplate1;

	@Resource
	SqlSessionTemplate sqlSessionTemplate2;

	public String getHello() {
		return "hello world from service!";
	}

	public void dbTest() {
		String str = "insert into users(name) values('name1'),('name2'),('name3')";
		jdbcTemplate.update(str);
	}

	public Object dbTest1() {
		String str = "select * from users limit 10";
		return jdbcTemplate.queryForList(str);
	}

	public Object dbTest2() throws FileNotFoundException {
		PageHelper.startPage(1, 20);
		List<Users> list = usersMapper.selectByExample(null);
		PageInfo<Users> pinfo = new PageInfo<>(list);
		System.out.println(pinfo.getTotal());
		// UsersExample ex = new UsersExample();
		// ex.createCriteria().andIdBetween(1, 20);
		// List<Users> list = usersMapper.selectByExample(ex);
		return list;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest3() throws Exception {
		UsersMapper usersMapper = sqlSessionTemplate1
				.getMapper(UsersMapper.class);
		Users user = new Users();
		user.setName("test123456");
		usersMapper.insert(user);
		((DemoService) AopContext.currentProxy()).dbTest4();
		return "done";
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest4() {
		UsersMapper usersMapper = sqlSessionTemplate2
				.getMapper(UsersMapper.class);
		Users user = new Users();
		user.setName("test654321");
		usersMapper.insert(user);
		// throw new RuntimeException("测试多数据源事务回滚");
		return "done";
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest5() {
		jdbcTemplate1
				.update("insert into users(name) values('testjdbc123456')");
		// try {
		((DemoService) AopContext.currentProxy()).dbTest6();
		// } catch (Exception e) {
		// System.err.println(e.getMessage());
		// }
		return "jdbc done!";
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest6() {
		jdbcTemplate2
				.update("insert into users(name) values('testjdbc654321')");
		throw new RuntimeException("测试多数据源事务回滚");
		// return "jdbc done!";
	}
}
