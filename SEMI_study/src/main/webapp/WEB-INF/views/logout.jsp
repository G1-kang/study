<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 제이쿼리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/logout.js"></script>

<!-- google -->
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id" content="1086121226988-79i2g3qsvsr85hmu6kh2i5jkelnofqrm.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script> <!-- 다른페이지에서 로그인된 정보를 불러오기 위해서  -->
<!-- kakao -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<title>Insert title here</title>
</head>
<body>
	<div>
		<span>|</span>
		<a href="mypage.do">마이페이지</a>
		<a href="boardlist.do">글목록</a>
		<a href="#">달력</a>
		<span>|</span>
		<a href="#" onclick="logoutGo();">로그아웃</a>
	</div>	
</body>
</html>