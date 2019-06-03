package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.dao.BlogDao;
import com.cafe24.jblog.dao.CategoryDao;
import com.cafe24.jblog.dao.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public Map<String, Object> getBlogContents(Map<String, Object> urlData) {
		
		Map<String, Object> urlDataMap = new HashMap<String, Object>();
		urlDataMap.put("userId", ""+urlData.get("id"));
		
		BlogVo blogVo = blogDao.get(urlDataMap);
		
		List<CategoryVo> categoryList = categoryDao.getList(urlDataMap);
		
		Optional<String> urlDataCategoryNo = (Optional<String>) urlData.get("categoryNo");
		String isCategoryNo = urlDataCategoryNo.isPresent() ? urlDataCategoryNo.get() : "-1";
		long categoryNo = isNumeric(isCategoryNo) ? Long.parseLong(isCategoryNo) : -1L;
		urlDataMap.put("categoryNo", categoryNo);
		List<PostVo> postList = postDao.getList(urlDataMap);

		Optional<String> urlDataPostNo = (Optional<String>) urlData.get("postNo");
		String isPostNo = urlDataPostNo.isPresent() ? urlDataPostNo.get() : "-1";
		long postNo = isNumeric(isPostNo) ? Long.parseLong(isPostNo) : -1L;
		urlDataMap.put("postNo", postNo);
		PostVo postVo = postDao.get(urlDataMap);
		
		Map<String, Object> blogContents = new HashMap<String, Object>();
		blogContents.put("blogVo", blogVo);
		blogContents.put("categoryList", categoryList);
		blogContents.put("postList", postList);
		blogContents.put("postVo", postVo);
		blogContents.put("selectedCategoryNo", categoryNo);
		
		return blogContents;
	}
	
	public BlogVo getAdminBlogInfo(String userId) {
		
		Map<String, Object> urlDataMap = new HashMap<String, Object>();
		urlDataMap.put("userId", userId);
		
		BlogVo result = blogDao.get(urlDataMap);
		return result;
	}
	
	public boolean updateDefaultBlogSetting(Map<String, Object> urlData) {
		
		Map<String, Object> urlDataMap = new HashMap<String, Object>();
		urlDataMap.put("userId", ""+urlData.get("userId"));
		
		BlogVo blogVo = (BlogVo) urlData.get("blogVo");
		
		if(blogVo.getTitle().equals(""))
			urlDataMap.put("title", "블로그의 제목이 비어있습니다.");
		else
			urlDataMap.put("title", blogVo.getTitle());
		
		if(blogVo.getLogo().equals(""))
			urlDataMap.put("logo", "assets/images/spring-logo.jpg");
		else
			urlDataMap.put("logo", blogVo.getLogo());
		
		boolean result = blogDao.update(urlDataMap);
		return result;
	}
	
	public List<CategoryVo> getAdminCategoryList(String userId){
		Map<String, Object> urlDataMap = new HashMap<String, Object>();
		urlDataMap.put("userId", userId);
		
		List<CategoryVo> result = categoryDao.getList(urlDataMap);
		return result;
	}
	
	public CategoryVo addCategory(CategoryVo categoryVo) {
		categoryDao.insert(categoryVo);
		CategoryVo result = categoryDao.getTop(categoryVo);
		return result;
	}
	
	public boolean deleteCategory(long categoryNo) {
		postDao.delete(categoryNo);
		boolean result = categoryDao.delete(categoryNo);
		return result;
	}
	
	public boolean addPost(PostVo postVo) {
		postDao.insert(postVo);
		boolean result = categoryDao.addPostCount(postVo.getCategoryNo());
		return result;
	}
}
