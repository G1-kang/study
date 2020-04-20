<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 제이쿼리 -->
<script type="text/javascript" src="resources/js/jquery-3.4.1.min.js"></script>

<script type="text/javascript">


</script>


</head>
<body>

	<div>
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" placeholder="aaa@naver.com으로 작성해주세요 " id="no_userId" name="userId" required="required">
					</td>
				</tr>
				<tr>
					<th>email</th>
					<td>
						<input type="email" placeholder="aaa@naver.com으로 작성해주세요 " name="userEmail" required="required">
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="userGender" checked="checked" value="MALE" /> MALE 
						<input type="radio" name="userGender" value="FEMALE"  />FEMALE
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<input type="text" placeholder="20201201" name="userDob" required="required">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text"  name="userName" required="required">
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						<input type="text"  name="userNick" required="required">
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<input type="button" value="수정하기"  > <!-- 수정하기 버튼 만들기  -->
						<input type="button" value="취소" onclick="location.href='index.jsp'">
					</td>
				</tr>
			</table>
	</div>

</body>
</html>