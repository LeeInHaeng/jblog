package com.cafe24.jblog.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.annotation.AuthAdmin;
import com.cafe24.jblog.vo.UserVo;

public class AuthAdminInterceptor extends HandlerInterceptorAdapter {

	private Map<String, String> pathVariables;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. Method의 @AuthAdmin 받아오기
		AuthAdmin authAdmin = handlerMethod.getMethodAnnotation(AuthAdmin.class);
		
		// 4. Method에 @AuthAdmin 가 없으면
		// Class(Type)에 @AuthAdmin을 받아오기
		if(authAdmin==null) {
			authAdmin = handlerMethod.getMethod().getAnnotation(AuthAdmin.class);
		}
		
		// 5. @AuthAdmin 이 안 붙어있는 경우 (Method에도 없고 클래스에도 없음)
		if(authAdmin==null) {
			return true;
		}
		
		// 6. 클래스나 메서드에 @AuthAdmin 이 붙어 있기 때문에 인증 여부를 확인
		HttpSession session = request.getSession();
		
		// url 얻어오기
		pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if(session == null || session.getAttribute("authUser")==null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}else {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(authUser.getId().equals(pathVariables.get("id"))) {
				return true;
			}
			else {
				request.setAttribute("message", "권한이 없습니다!");
				request.setAttribute("url", "main");
				request.getRequestDispatcher(request.getContextPath() + "/main/redirect").forward(request, response);
		        return false;
			}
		}
		//return true;
	}
}
