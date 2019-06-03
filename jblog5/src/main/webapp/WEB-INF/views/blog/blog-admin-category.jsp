<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		      	<table class="admin-cat">
		      		<thead>
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		</thead>
		      		<tbody>
		      		<c:forEach items='${categoryVo }' var ='vo' varStatus='status'>
		      			<tr id="admin-cat-${vo.no }">
						<td>${vo.no }</td>
						<td>${vo.name }</td>
						<td>${vo.postCount }</td>
						<td>${vo.description }</td>
						<td>
							<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"
								class="delete-img-button" id="${vo.no }-delete-img-button"/>
						</td>
						</tr>
					</c:forEach>	
					</tbody>			  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" id="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description" id="description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가" id="categoryAddBtn"></td>
		      		</tr>      		      		
		      	</table> 
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp">
			<c:param name="title" value="${blogVo.title }" />
			<c:param name="userId" value="${blogVo.userId }" />
		</c:import>
	</div>
	<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script>
		$(function(){
			$("#categoryAddBtn").click(function(){
				var name = $("#name").val();
				var description = $("#description").val();
				
				if(name===""){
					alert("카테고리명을 입력해 주세요.");
					$("#name").focus();
					return;
				}
				if(description===""){
					alert("카테고리의 설명을 입력해 주세요.");
					$("#description").focus();
					return;
				}
				
				var categoryData = {
					name: name,
					description: description,
					userId: '${blogVo.userId }'
				};
				
				  $.ajax( {
						contentType: "application/json; charset=UTF-8",
					    url : "${pageContext.servletContext.contextPath }/${blogVo.userId }/admin/api/category/add",
					    type: "post",
					    dataType: "json",
					    data: JSON.stringify(categoryData),
					    success: function( response ){
					       if(response.result !== "success"){
					    	   alert(response.message);
					    	   return;
					       }
       
					       $(".admin-cat tbody").prepend("<tr id='admin-cat-"+response.data.no+"'>"+
					    		   "<td>"+response.data.no+"</td>"+
					    		   "<td>"+response.data.name+"</td>"+
					    		   "<td>0</td>"+
					    		   "<td>"+response.data.description+"</td>"+
					    		   "<td>"+
					    		   "<img src='${pageContext.request.contextPath}/assets/images/delete.jpg' "+
									"class='delete-img-button' id='"+response.data.no+"-delete-img-button'/></td>"+
					    		   "</tr>");
					       $("#name").val("");
					       $("#description").val("");
					    },
					    error: function( jqXHR, status, error ){
					       console.error( status + " : " + error );
					    }

					  });
				
			});
			
			$(document).on("click",".delete-img-button",function(e){
				var deleteConfirm = confirm("카테고리를 삭제하면 그 안의 게시물들도 모두 삭제됩니다.\n삭제하시겠습니까?");
				if(!deleteConfirm)
					return;
				var categoryNo = e.target.id.split("-")[0];

				  $.ajax( {
						contentType: "application/json; charset=UTF-8",
					    url : "${pageContext.servletContext.contextPath }/${blogVo.userId }/admin/api/category/delete/"+categoryNo,
					    type: "get",
					    dataType: "json",
					    data: "",
					    success: function( response ){
					       if(response.result !== "success"){
					    	   alert(response.message);
					    	   return;
					       }
					       $("#admin-cat-"+response.data).remove();
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