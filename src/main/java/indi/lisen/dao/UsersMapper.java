package indi.lisen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.lisen.model.Users;
import indi.lisen.model.UsersExample;

public interface UsersMapper {
	long countByExample(UsersExample example);

	int deleteByExample(UsersExample example);

	int insert(Users record);

	int insertSelective(Users record);

	List<Users> selectByExample(UsersExample example);

	int updateByExampleSelective(@Param("record") Users record,
			@Param("example") UsersExample example);

	int updateByExample(@Param("record") Users record,
			@Param("example") UsersExample example);
}