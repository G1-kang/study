<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 제이쿼리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/login.js"></script>
<!-- google -->
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id" content="1086121226988-79i2g3qsvsr85hmu6kh2i5jkelnofqrm.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<!-- kakao -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

</head>
<body>
<h1>로그인페이지</h1>
<form action="login.do" method="post" onsubmit="return validate();">
	<table>
		<tr>
			<th>아이디</th>
			<td>
				<input type="email" name="sduemail" id="sduemail">
			</td>	
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<input type="password" name="sdupw" id="sdupw">
			</td>	
		</tr>
		<tr>
			<td colspan="2" align="right">
				<input type="submit" value="로그인">
				<input type="button" value="취소" onclick="location.href='main.do'">	
			</td>
		</tr>
	</table>
	
	<!-- 카카오 로그인, 구글 로그인 -->
	<div>
		<a href="#" onclick="kakao();"><img src="${pageContext.request.contextPath}/resources/IMG/kakao_login_btn.png" alt="카카오로그인"></a> 
	</div>
		<div class="g-signin2" data-width="220"  data-height="45" data-onsuccess="onSignIn" data-longtitle="true"></div>	

	<br/>
	
</form>
</body>
</html>