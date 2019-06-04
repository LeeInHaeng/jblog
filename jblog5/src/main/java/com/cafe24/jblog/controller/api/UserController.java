package com.cafe24.jblog.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.service.UserService;

@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkId")
	public Map<String, Object> checkId(
			@RequestParam(value="id", required=true, defaultValue="") String id) {
		
		Boolean exist = userService.checkExistId(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", exist);
		
		return map;
	}
}
