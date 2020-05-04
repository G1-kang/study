<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div>
		<f:form action="updatemydata1.do" method="post" modelAttribute="sduserdto" name="f">
			<input type="hidden" name="sduseq" value="${sduserdto.sduseq}"/>		
			<input type="hidden" name="sdupw" value="${sduserdto.sdupw}"/>
			<table>
				<tr>
					<th>아이디</th>
					<td>
					<f:input type="text" placeholder="	${sduserdto.sduemail}"  path="sduemail" readonly="true"/>
					</td>
					<td>
					<f:errors path="sduemail" ></f:errors>
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td><!-- checked="${sduserdto.sdusex eq 'M' ? 'checked' : ''}" -->
 						<f:checkbox  path="sdusex"  value="M" /> 남자 
 						<f:checkbox  path="sdusex"  value="F" />여자 
 					</td>
 					<td>	
 						<f:errors path="sdusex"></f:errors>
					
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<f:input type="text" path="sdudob"/>
					</td>
					<td>	
						<f:errors path="sdudob" ></f:errors>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
					
						<f:input type="text"  path="sduname" />
					</td>
					<td>	
						<f:errors path="sduname" ></f:errors>
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						<f:input type="text"  path="sdunick"/>
					</td>
					<td>	
						<f:errors path="sdunick" ></f:errors>
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<input type="submit" value="회원정보수정" />
						<input type="button" value="취소" onclick="location.href='main.do'"/>
					</td>
				</tr>
			</table>
		</f:form>
	</div>
	
	 <!--f태그 속성들 
	 id : 별도로 지정하지 않으면 commandName과 같은 값이 됩니다.
name : 별도로 지정하지 않으면 commandName과 같은 값이 됩니다.
action : 별도로 지정하지 않으면 현재 페이지의 주소가 됩니다.

					  -->
	
</body>
</html>