package com.cafe24.jblog.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.UserVo;

@Controller("blogAPIController")
public class BlogController {
	
	@Autowired
	private BlogService blogService;

	@ResponseBody
	@RequestMapping(value="/{id:(?!assets).*}/admin/api/category/add", method=RequestMethod.POST)
	public Map<String, Object> categoryAdd(
			@RequestBody CategoryVo categoryVo,
			HttpSession session){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(authUser.getId().equals(categoryVo.getUserId())) {
				result.put("result","success");
				result.put("data", blogService.addCategory(categoryVo));
				return result;
			}else {
				result.put("result","denied");
				result.put("message","권한이 없습니다!");
				return result;
			}	
		}else {
			result.put("result","denied");
			result.put("message","인증된 사용자가 아닙니다!");
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping("/{id:(?!assets).*}/admin/api/category/delete/{categoryNo}")
	public Map<String, Object> categoryDelete(
			@PathVariable("id") String userId,
			@PathVariable("categoryNo") long categoryNo,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(authUser.getId().equals(userId)) {
				blogService.deleteCategory(categoryNo);
				result.put("result","success");
				result.put("data", categoryNo);
				return result;
			}else {
				result.put("result","denied");
				result.put("message","권한이 없습니다!");
				return result;
			}	
		}else {
			result.put("result","denied");
			result.put("message","인증된 사용자가 아닙니다!");
			return result;
		}
	}
}
