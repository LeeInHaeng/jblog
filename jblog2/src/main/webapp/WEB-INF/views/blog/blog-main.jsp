<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("newline", "\n");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
	<c:set var="postVo" value='${blogContents["postVo"] }' />
	<c:set var="postList" value='${blogContents["postList"] }' />
	<c:set var="categoryList" value='${blogContents["categoryList"] }'/>
	<c:set var="blogVo" value='${blogContents["blogVo"] }' />
	<c:set var="selectedCategoryNo" value='${blogContents["selectedCategoryNo"] }' />
	
		<c:import url="/WEB-INF/views/includes/header.jsp">
			<c:param name="title" value="${blogVo.title }" />
			<c:param name="userId" value="${blogVo.userId }" />
			<c:param name="authUser" value="${sessionScope.authUser.id}" />
		</c:import>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test='${postVo==null }'>
							<h4>게시글이 아직 존재하지 않습니다.</h4>
							<p>
								게시글이 아직 존재하지 않습니다. 추후에 추가해 주시길 바랍니다.
							</p>
						</c:when>
						<c:otherwise>
							<h4>${fn:replace(postVo.title, newline, "<br>" )}</h4>
							<p>
								${fn:replace(postVo.contents, newline, "<br>" )}
							</p>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:if test="${empty postList}">
						<li>게시글이 아직 존재하지 않습니다.</li>
					</c:if>
					<c:if test="${not empty postList }">
						<c:forEach items='${postList }' var ='vo' varStatus='status'>
							<c:choose>
								<c:when test='${postVo.no==vo.no }'>
									<li class="selected">
								</c:when>
								<c:otherwise>
									<li>
								</c:otherwise>
							</c:choose>
										<a href="${pageContext.request.contextPath}/${blogVo.userId}/${vo.categoryNo}/${vo.no}">
											${vo.title }
										</a>
									</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/${blogVo.logo}">
			</div>
		</div>
		
		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
			<c:forEach items='${categoryList }' var ='vo' varStatus='status'>
			<c:choose>
				<c:when test='${selectedCategoryNo == vo.no }'>
					<li class="selected">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
						<a href="${pageContext.request.contextPath}/${blogVo.userId}/${vo.no}">
							${vo.name }
						</a>
					</li>
			</c:forEach>
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp">
			<c:param name="title" value="${blogVo.title }" />
			<c:param name="userId" value="${blogVo.userId }" />
		</c:import>
	</div>
</body>
</html>