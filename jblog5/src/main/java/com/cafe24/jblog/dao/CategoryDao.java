package com.cafe24.jblog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.UserVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean addPostCount(long categoryNo) {
		int count = sqlSession.update("category.addPostCount", categoryNo);
		return 1==count;
	}
	
	public boolean insertByDefault(UserVo userVo) {
		int count = sqlSession.insert("category.insertByDefault", userVo);
		return 1==count;
	}
	
	public List<CategoryVo> getList(Map<String, Object> data){
		List<CategoryVo> result = sqlSession.selectList("category.getList", data);
		return result;
	}
	
	public boolean insert(CategoryVo categoryVo) {
		int count = sqlSession.insert("category.insert", categoryVo);
		return 1==count;
	}
	
	public CategoryVo getTop(CategoryVo categoryVo) {
		CategoryVo result = sqlSession.selectOne("category.getTop", categoryVo);
		return result;
	}
	
	public boolean delete(long categoryNo) {
		int result = sqlSession.delete("category.delete", categoryNo);
		return result==1;
	}
}
