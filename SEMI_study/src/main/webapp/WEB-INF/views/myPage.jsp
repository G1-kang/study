<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/logout.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/JS/mypage.js"></script>
<title>Insert title here</title>
</head>
<%@include file ="/WEB-INF/views/logout.jsp" %>
<body>


<h1>마이페이지</h1>
<p>${member.sdunick }님의 마이페이지 입니다 </p>

	<div id="updatebefore">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						${member.sduemail }
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="sdusex" value="M" checked="${memeber.sdusex eq 'M' ?  'checked' : ''}" disabled="disabled" /> 남자
						<input type="radio" name="sdusex" value="F" checked="${memeber.sdusex eq 'F' ?  'checked' : ''}" disabled/>여자
					
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						${member.sdudob}
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						${member.sduname}
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						${member.sdunick}
					</td>
				</tr>
				<tr>
					<td colspan="5" align="right">
						<input type="button" value="수정하기" onclick="location.href='updatemypage.do'" />
						<input type="button" value="취소" onclick="location.href='main.do'"/>
					</td>
				</tr>
			</table>
	</div>

</body>
</html>