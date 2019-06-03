package com.cafe24.jblog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {

	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> getList(Map<String, Object> data){
		List<PostVo> result = sqlSession.selectList("post.getList", data);
		return result;
	}
	
	public PostVo get(Map<String, Object> data) {
		PostVo result = sqlSession.selectOne("post.get", data);
		return result;
	}
	
	public boolean delete(long categoryNo) {
		int result = sqlSession.delete("post.delete", categoryNo);
		return result==1;
	}
	
	public boolean insert(PostVo postVo) {
		int result = sqlSession.insert("post.insert", postVo);
		return result==1;
	}
}
