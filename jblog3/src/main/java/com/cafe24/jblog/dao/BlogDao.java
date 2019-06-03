package com.cafe24.jblog.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.UserVo;

@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo userVo) {
		int count = sqlSession.insert("blog.insert", userVo);
		return 1==count;
	}
	
	public BlogVo get(Map<String, Object> data) {
		BlogVo result = sqlSession.selectOne("blog.get", data);
		return result;
	}
	
	public boolean update(Map<String, Object> data) {
		int count = sqlSession.update("blog.update", data);
		return 1==count;
	}
}
