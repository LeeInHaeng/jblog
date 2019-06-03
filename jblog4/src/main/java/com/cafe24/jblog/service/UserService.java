package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.dao.BlogDao;
import com.cafe24.jblog.dao.CategoryDao;
import com.cafe24.jblog.dao.UserDao;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	public boolean userJoin(UserVo userVo) {
		Boolean[] results = new Boolean[3];
		results[0] = userDao.insert(userVo);
		results[1] = blogDao.insert(userVo);
		results[2] = categoryDao.insertByDefault(userVo);
		
		for(boolean result : results) {
			if(result==false)
				return false;
		}
		return true;
	}
	
	public boolean checkExistId(String id) {
		UserVo userVo = userDao.getById(id);
		return userVo != null;
	}
	
	public UserVo userLogin(UserVo userVo) {
		UserVo authUser = userDao.login(userVo);
		return authUser;
	}
}
