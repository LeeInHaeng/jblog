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
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp">
			<c:param name="title" value="${blogVo.title }" />
			<c:param name="userId" value="${blogVo.userId }" />
			<c:param name="authUser" value="${sessionScope.authUser.id}" />
		</c:import>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp">
					<c:param name="userId" value="${blogVo.userId }" />
					<c:param name="menu" value="basic" />
				</c:import>
				<form:form modelAttribute="postVo"
					action="${pageContext.request.contextPath}/${blogVo.userId }/admin/write" method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<form:input path="title"/>
				      			<form:select path="categoryNo">
									<form:options items="${categoryVo}" itemLabel="name" itemValue="no" />
								</form:select>
								<p style="font-weight:bold; color:red; text-align:left; padding:0">
									<form:errors path="title" />
								</p>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td>
			      				<form:textarea path="contents"/>
								<p style="font-weight:bold; color:red; text-align:left; padding:0">
									<form:errors path="contents" />
								</p>
			      			</td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp">
			<c:param name="title" value="${blogVo.title }" />
			<c:param name="userId" value="${blogVo.userId }" />
		</c:import>
	</div>
</body>
</html>