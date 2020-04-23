<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>마이페이지</h1>
<p>${member.sdunick }님의 마이페이지 입니다 </p>
<input type="button" value="로그아웃" onclick="location.href='logout.do'">
</body>
</html>