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
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/menu.jsp">
			<c:param name="authUser" value="${sessionScope.authUser.id}" />
		</c:import>
		<form:form modelAttribute="userVo"
			class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			
			<label class="block-label" for="name">이름</label>
			<form:input path="name"/>
			<p style="font-weight:bold; color:red; text-align:left; padding:0">
				<form:errors path="name" />
			</p>
			
			<label class="block-label" for="id">아이디</label>
			<form:input path="id"/> 
			<input id="check-button" type="button" value="id 중복체크">
			<img id="check-image" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="font-weight:bold; color:red; text-align:left; padding:0">
				<form:errors path="id" />
			</p>

			<label class="block-label" for="pass">패스워드</label>
			<form:password path="pass" />
			<p style="font-weight:bold; color:red; text-align:left; padding:0">
				<form:errors path="pass" />
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
	
	<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script>
	
	$(function(){
		$('#id').change(function(){
			$("#check-button").show();
		    $("#check-image").hide();
		});
		$("#check-button").click( function(){
			var id = $("#id").val();
			if(id == ''){
				return;
			}
			  $.ajax( {
				contentType: "application/json; charset=UTF-8",
			    url : "${pageContext.servletContext.contextPath }/user/api/checkId?id="+id,
			    type: "get",
			    dataType: "json",
			    data: "",
			    success: function( response ){
			       if(response.result !== "success"){
			    	   //console.log(response.message);
			    	   return;
			       }
			       
			       if(response.data===true){
			    	   alert("이미 존재하는 이메일 입니다.");
			    	   $("#id").focus();
			    	   $("#id").val("");
				       return;
			       }
			       
			       $("#check-button").hide();
			       $("#check-image").show();
			    },
			    error: function( jqXHR, status, error ){
			       console.error( status + " : " + error );
			    }

			  });

		});	
	});
	
	</script>
	
</body>
</html>
