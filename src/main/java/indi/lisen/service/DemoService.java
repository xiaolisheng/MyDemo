package indi.lisen.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import indi.lisen.dao.UsersMapper;
import indi.lisen.model.Users;

@Service
public class DemoService {
	// @Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	UsersMapper usersMapper;

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

	public Object dbTest2() {
		PageHelper.startPage(1, 20);
		List<Users> list = usersMapper.selectByExample(null);
		PageInfo<Users> pinfo = new PageInfo<>(list);
		System.out.println(pinfo.getTotal());
		// UsersExample ex = new UsersExample();
		// ex.createCriteria().andIdBetween(1, 20);
		// List<Users> list = usersMapper.selectByExample(ex);
		return list;
	}

	@Resource
	JtaTransactionManager transactionManager;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest3() throws Exception {
		UserTransaction userTx = transactionManager.getUserTransaction();
		userTx.begin();
		try {
			Users user = new Users();
			user.setName("test123456");
			usersMapper.insert(user);
			throw new Exception("测试多数据源事务回滚");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			userTx.rollback();
		}

		// try {
		// ((DemoService) AopContext.currentProxy()).dbTest4();
		// } catch (Exception e) {
		// System.err.println(e.getMessage());
		// }
		return "done";
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object dbTest4() {
		Users user = new Users();
		user.setName("test654321");
		usersMapper.insert(user);
		throw new RuntimeException("测试多数据源事务回滚");
		// return "done";
	}
}
