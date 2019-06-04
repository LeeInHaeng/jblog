package com.cafe24.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.annotation.AuthAdmin;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.FileuploadService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileuploadService fileuploadService;

	@RequestMapping(value= {"/{id:(?!assets).*}","/{id:(?!assets).*}/{categoryNo}","/{id:(?!assets).*}/{categoryNo}/{postNo}"})
	public String blogMain(
			@PathVariable(value="id") String id,
			@PathVariable(value="categoryNo") Optional<String> categoryNo,
			@PathVariable(value="postNo") Optional<String> postNo,
			ModelMap model) {
		
		Map<String, Object> urlData = new HashMap<String, Object>();
		urlData.put("id", id);
		urlData.put("categoryNo", categoryNo);
		urlData.put("postNo", postNo);
		
		Map<String, Object> blogContents = blogService.getBlogContents(urlData);
		model.addAttribute("blogContents", blogContents);
		
		if(blogContents.get("blogVo")==null) {
			model.addAttribute("message", "존재하지 않는 사용자 입니다.");
			model.addAttribute("url", "main");
			return "main/redirect";
		}
		
		return "blog/blog-main";
	}
	
	@AuthAdmin
	@RequestMapping(value= {"/{id:(?!assets).*}/admin", "/{id:(?!assets).*}/admin/basic"}, method=RequestMethod.GET)
	public String adminBasic(
			@PathVariable(value="id") String id,
			Model model,
			HttpSession session) {
		
		BlogVo blogVo = blogService.getAdminBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@AuthAdmin
	@RequestMapping(value= {"/{id:(?!assets).*}/admin", "/{id:(?!assets).*}/admin/basic"}, method=RequestMethod.POST)
	public String adminBasic(
			@PathVariable(value="id") String id,
			BlogVo blogVo,
			@RequestParam(value="file1", required=false) MultipartFile multipartFile,
			BindingResult result,
			Model model,
			HttpSession session) {
		
		
		String url = fileuploadService.restore(multipartFile);
		blogVo.setLogo(url);
				
		Map<String, Object> urlData = new HashMap<String, Object>();
		urlData.put("userId", id);
		urlData.put("blogVo", blogVo);
		blogService.updateDefaultBlogSetting(urlData);
				
		return "redirect:/"+id;
	}
	
	@AuthAdmin
	@RequestMapping(value="/{id:(?!assets).*}/admin/category", method=RequestMethod.GET)
	public String adminCategory(
			@PathVariable(value="id") String id,
			Model model,
			HttpSession session) {
		
		BlogVo blogVo = blogService.getAdminBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
				
		List<CategoryVo> categoryVo = blogService.getAdminCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
				
		return "blog/blog-admin-category";
	}
	
	@AuthAdmin
	@RequestMapping(value="/{id:(?!assets).*}/admin/write", method=RequestMethod.GET)
	public String adminWrite(
			@PathVariable(value="id") String id,
			@ModelAttribute PostVo postVo,
			Model model,
			HttpSession session) {
		
		BlogVo blogVo = blogService.getAdminBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
				
		List<CategoryVo> categoryVo = blogService.getAdminCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
				
		return "blog/blog-admin-write";
	}
	
	@AuthAdmin
	@RequestMapping(value="/{id:(?!assets).*}/admin/write", method=RequestMethod.POST)
	public String adminWrite(
			@PathVariable(value="id") String id,
			@ModelAttribute @Valid PostVo postVo,
			BindingResult result,
			Model model,
			HttpSession session) {
		

		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
					
			BlogVo blogVo = blogService.getAdminBlogInfo(id);
			model.addAttribute("blogVo", blogVo);
					
			List<CategoryVo> categoryVo = blogService.getAdminCategoryList(id);
			model.addAttribute("categoryVo", categoryVo);
					
			return "blog/blog-admin-write";
		}
				
		blogService.addPost(postVo);
				
		return "redirect:/"+id;
	}
}
