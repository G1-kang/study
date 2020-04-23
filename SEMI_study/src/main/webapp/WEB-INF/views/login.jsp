<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 제이쿼리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<script type="text/javascript">

function validate() {
	var sduemail = document.getElementById("sduemail").value;
	var sdupw = document.getElementById("sdupw").value;
	
	if(sduemail == ""){
		alert('아이디를 입력해 주세요 ');
		return false;
	}else if(sdupw == ""){
		alert("비밀번호를 입력해 주세요 ");
		return false;
	}
	
}

</script>

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
</form>
</body>
</html>