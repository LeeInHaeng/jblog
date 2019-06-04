<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/menu.jsp">
			<c:param name="authUser" value="${sessionScope.authUser.id}" />
		</c:import>
		<form:form modelAttribute="userVo"
		class="login-form" method="post" action="${pageContext.request.contextPath}/user/login">
      		<label>아이디</label> <form:input path="id"/>
      		<p style="font-weight:bold; color:red; text-align:left; padding:0">
				<form:errors path="id" />
			</p>
			
      		<label>패스워드</label> <form:password path="pass"/>
      		<p style="font-weight:bold; color:red; text-align:left; padding:0">
				<form:errors path="pass" />
			</p>
			
      		<input type="submit" value="로그인">
		</form:form>
	</div>
</body>
</html>
