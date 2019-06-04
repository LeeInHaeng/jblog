package com.cafe24.jblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo userVo) {
		int count = sqlSession.insert("user.insert", userVo);
		return 1==count;
	}
	
	public UserVo getById(String id) {
		UserVo userVo = sqlSession.selectOne("user.getById", id);
		return userVo;
	}
	
	public UserVo login(UserVo userVo) {
		UserVo authUser = sqlSession.selectOne("user.login", userVo);
		return authUser;
	}
}
