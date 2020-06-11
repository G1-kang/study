<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file ="/WEB-INF/views/logout.jsp" %>
<!-- 로그아웃 -->
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/logout.js"></script>

<%
	String msg = (String)request.getAttribute("msg");
%>
<script>

console.log('${msg}'+'내용이 나와야한다');

    $(function(){
    	alert("'" + <%=msg%> + "'");
    	 
    	
        var responseMessage = $('#msg').val();
        if(responseMessage != ""){
        
            alert(responseMessage);
        }
    });
 
</script>

</head>


<body>
<input type="text" id="msg" value="${msg }">
<div id="container" style="width:100%">

<div id="header" style="background-color:#CE9FFC;">
<h1 style="margin-bottom:0;">Main Title of Web Page</h1></div>


<div id="menu" style="background-color:#EEEEEE;height:700px;width:20%;float:left;">

<div style="margin-left: 20px; margin-top: 10px; width: 100%">
<a href="allwrite.do">전체 글</a><br>
<a href="main.do">내글 보기 </a><br>
</div>

</div>

<div id="content" style="background-color:white-space;height:700px;width:80%;float:left;">
<div style="margin-left: 40px; margin-top: 40px; margin-bottom: 40px;">
글 목록

	<form action="/boardlist.do" method="get">
		<label>글 검색 :</label>
		<select name="category" id="category">
			<option value="sdbtitle">제목</option>
			<option value="sdubcontent">제목</option>
			<option value="sdunick">제목</option>
		</select>
		<input type="text" size="20">
		<input type="submit" value="검색하기">
	</form>
	
	<table border="1">
	<col width="100px">
	<col width="300px">
	<col width="100px">
	<col width="100px">
	<col width="100px">
	<col width="100px">

	<tr>
		<th>no</th>
		<th>제목</th>
		<th>작성자</th>
		<th>등록일</th>
		<th>조회수</th>
		<th>좋아요</th>
	</tr>
	<c:choose>
		<c:when test="${empty boardlist}">
		<tr>
		<td colspan="6">-----------등록된 글이 없습니다-----------</td>
		</tr>
		</c:when>
	
	<c:otherwise>
		<c:forEach items="${boardlist}" var="list">
			<tr>
				<td>${list.sdbseq }</td>
				<td><a href="board.do?sdbseq=${list.sdbseq }">${list.sdbtitle }</a></td>
				<td>${list.sduemail }</td>
				<td>${list.sdbregdate }</td>
				<td>${list.sdbviews }</td>
				<td>${list.sdblike }</td>
			</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>
</table>
		  <input type="button" value="글쓰기"onclick="location.href='boardwrite.do'"/>
		  <input type="button" value="메인으로" onclick="location.href='main.do'"/>

</div>
</div>

<div id="footer" style="background-color:#CE9FFC;clear:both;text-align:center;">
Copyright ©semi.com</div>

</div>


</body>
</html>