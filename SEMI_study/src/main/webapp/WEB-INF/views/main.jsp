<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<!-- 로그아웃 -->
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/logout.js"></script>
</head>
<%@include file ="/WEB-INF/views/logout.jsp" %>
<body>


<div>
	<h1>메인 페이지 임 </h1>
	<input type="button" value="로그인" onclick="location.href='loginPage.do'">
	<input type="button" value="회원가입" onclick="location.href='joinform.do'"> 
	<input type="button" value="글목록" onclick="location.href='boardlist.do'">
</div>


</body>
</html>