<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 제이쿼리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<!-- ${pageContext.request.contextPath} -->


<!-- 회원가입폼, 이메일 인증 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JS/join.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JS/emailChk.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/emailChk.css">


</head>
<body>

	<div>
		<form action="SduserRegist.do" method="post" name="regist" onsubmit="return vaildate();">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" placeholder="aaa@naver.com으로 작성해주세요 " id="no_sduemail" name="sduemail">
						<input type="button" value="중복검사" onclick="nomal_idChk();">
						<input type="button" value="이메일 인증 " id="Ath" onclick="modalEmailAuth();">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<span id="emailConfirm"></span>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="text" id="no_sdupw" name="sdupw" >
					</td>
				</tr>
				<tr>
					<th>비밀번호재입력</th>
					<td>
						<input type="text"  id="no_sdupwch" name="sdupwch" >
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<span id="pwConfirm"></span>
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="sdusex" checked="checked" value="M" /> 남자 
						<input type="radio" name="sdusex" value="F" />여자
					
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<input type="text" placeholder="20201201" id="no_sdudob" name="sdudob">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" id="no_sduname" name="sduname" >
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						<input type="text" id="no_sdunick" name="sdunick">
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<input type="submit" value="회원가입" >
						<input type="button" value="취소" onclick="location.href='main.do'">
					</td>
				</tr>
			</table>
		</form>
	</div>
		    <!-- The Modal -->
    <div id="myModal" class="modal">
      	<!-- Modal content -->
      	<div id="modal-content" class="modal-content">
		</div>
	</div>
	
</body>
</html>