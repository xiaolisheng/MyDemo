package indi.lisen;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import indi.lisen.dao.UsersMapper;

public class Main {
	public static void main(String[] args) throws Exception {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsReader("mybatis-config.xml"));
		SqlSession session = factory.openSession();
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		System.out.println(mapper.selectByExample(null).size());;
	}
}
