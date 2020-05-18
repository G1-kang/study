<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"  src="${pageContext.request.contextPath}/resources/JS/uploadimg.js"></script>

<title>Insert title here</title>

</head>
<body>

	<div>
		<f:form action="updatemydata.do" method="post" modelAttribute="member" name="f" enctype="multipart/form-data" >
			<input type="hidden" name="sduseq" value="${member.sduseq}"/>		
			<input type="hidden" name="sdupw" value="${member.sdupw}"/>
			<input type=hidden id="sduimgpath" name="sduimgpath" value="${member.sduimgpath }"/>

			
			<table>
				<tr>
					<td colspan="2" align="center" id="imgtd">
						<c:choose>
							<c:when test="${empty member.sduimgpath}">
								<img src="${pageContext.request.contextPath}/resources/IMG/defualtperson.PNG" width="200" height="200">
							</c:when>
						<c:otherwise>
								<img src="${member.sduimgpath}" width="200" height="200" >
						</c:otherwise>
						</c:choose>
					
					</td>
				</tr>
			   <tr>
					<td colspan="2"> 
						<f:input type="file" value="새로운 사진 올리기" id="userImgFile" path="userImgFile" onclick="imgUpload();"/> 
					</td>
				</tr>
			
				<tr>
					<th>아이디</th>
					<td>
					<f:input type="text" placeholder="	${member.sduemail}"  path="sduemail" readonly="true"/>
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