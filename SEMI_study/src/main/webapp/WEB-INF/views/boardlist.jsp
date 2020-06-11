<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file ="/WEB-INF/views/logout.jsp" %>
<!-- 로그아웃 -->
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/logout.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/JS/boardlist.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


</head>


<body>
<input type="text" id="msg" value="${msg }">
<div id="container" style="width:100%">

<div id="header" style="background-color:#CE9FFC;">
<h1 style="margin-bottom:0;">Main Title of Web Page</h1></div>


<div id="menu" style="background-color:#EEEEEE;height:700px;width:20%;float:left;">

<div style="margin-left: 20px; margin-top: 10px; width: 100%">
<a href="allwrite.do">전체 글</a><br>
<a href="main.do">내글 보기 </a><br>
</div>

</div>

<div id="content" style="background-color:white-space;height:700px;width:80%;float:left;">
<div style="margin-left: 40px; margin-top: 40px; margin-bottom: 40px;">
글 목록

	<form action="boardlist.do" method="get">
		<label>글 검색 :</label>
		<select name="category" id="category">
			<option value="sdbtitle">제목</option>
			<option value="sdubcontent">내용</option>
			<option value="sdunick">닉네임</option>
		</select>
		<input type="text" size="20" name="keyword">
		<input type="submit" value="검색하기">
	</form>
	<input type="button" onclick="location.href='boardlist.do'" value="전체글보기">
	<input type="button" onclick="location.href='boardlist.do?sduemail=${login.sduemail}'" value="내글보기">
	
	
	<table border="1">
	<col width="100px">
	<col width="300px">
	<col width="100px">
	<col width="100px">
	<col width="100px">
	<col width="100px">

	<tr>
		<th>no</th>
		<th>제목</th>
		<th>작성자</th>
		<th>등록일</th>
		<th>조회수</th>
		<th>좋아요</th>
	</tr>
	<c:choose>
		<c:when test="${empty boardlist}">
		<tr>
		<td colspan="6">-----------등록된 글이 없습니다-----------</td>
		</tr>
		</c:when>
	
	<c:otherwise>
		<c:forEach items="${boardlist}" var="list">
			<tr>
				<td>${list.sdbseq }</td>
				<td><a href="board.do?sdbseq=${list.sdbseq }">${list.sdbtitle }</a></td>
				<td>${list.sduemail }</td>
				<td>${list.sdbregdate }</td>
				<td>${list.sdbviews }</td>
				<td>${list.sdblike }</td>
			</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>
</table>
		  <input type="button" value="글쓰기"onclick="location.href='boardwrite.do'"/>
		  <input type="button" value="메인으로" onclick="location.href='main.do'"/>

</div>
</div>



</body> 

<!-- 페이징 영역 -->
   <footer id="paging">
         <div class="col-md-2"></div>
         <div class="col-md-8">
            <ul class="pagination">
            
               <!-- << : 10 페이지 뒤로-->
               <c:if test="${pagination.startPage >= 11 }">
                  <li onClick="paging()">
                     <a href="boardlist.do?currentPage=${pagination.currentPage -10}" aria-label="Previous">
                        <span aria-hidden="true">&lt;&lt;</span>
                     </a>
                  </li>
               </c:if>
               
               <!-- < -->
               <c:if test="${pagination.currentPage ne 1 }">
                  <li onClick="paging(${pagination.prevPage })">
                     <a href="boardlist.do?currentPage=${pagination.prevPage }" aria-label="Previous">
                        <span aria-hidden="true">&nbsp;&lt;&nbsp;</span>
                     </a>
                  </li>
               </c:if>
               
               <!-- 처음 : ... 1 -->
               <c:if test="${pagination.currentPage > 6 }">
                  <li onClick="paging(1)">
                     <a href="boardlist.do?currentPage=1" aria-label="Previous">
                        <span aria-hidden="true">1</span>
                     </a>
                  </li>
                  <li class="none">
                     <span aria-hidden="true">...</span>
                  </li>
               </c:if>
               
               <!-- 번호 출력 -->
               <c:forEach var="pageNum" begin="${pagination.startPage }" end="${pagination.endPage }">
                  <li class="page-item  <c:out value="${pagination.currentPage == pageNum ? 'active' : ''}"/>" id="<c:out value="${pagination.currentPage == pageNum ? 'none' : ''}"/>" onClick="paging('${pageNum }')">
                     <a class="page-link" href="boardlist.do?currentPage=${pageNum }">${pageNum }</a>
                  </li>
               </c:forEach>
               
               <!-- 끝 : ... N  -->
               <c:choose>
                  <c:when test="${pagination.endPage ne pagination.totalPage && pagination.totalPage > 10}">
                     <li class="none">
                        <span aria-hidden="true">...</span>
                     </li>
                     <li onClick="paging(${pagination.totalPage })">
                        <a href="main.do?currentPage=${pagination.totalPage }" aria-label="Next"> 
                           <span aria-hidden="true">${pagination.totalPage }</span>
                        </a>
                     </li>
                  </c:when>
                  <c:otherwise>
                  </c:otherwise>
               </c:choose>
               
               <!-- > -->
               <c:if test="${pagination.currentPage ne pagination.totalPage }">
                  <li onClick="paging(${pagination.nextPage })">
                     <a href="main.do?currentPage=${pagination.nextPage }" aria-label="Next"> 
                        <span aria-hidden="true">&nbsp;&gt;&nbsp;</span>
                     </a>
                  </li>
               </c:if>
               
               <!-- >> : 10 페이지 앞으로-->
               <c:if test="${(pagination.currentPage +10) <= pagination.totalPage }">
                  <li onClick="paging(${pagination.nextPage })">
                     <a href="main.do?currentPage=${pagination.currentPage +10 }" aria-label="Next"> 
                        <span aria-hidden="true">&gt;&gt;</span>
                     </a>
                  </li>
               </c:if>
               
            </ul>
         </div>
         <div class="col-md-2"></div>
   </footer>
   <!-- 페이징 영역 끝 -->
<div id="footer" style="background-color:#CE9FFC;clear:both;text-align:center;">
Copyright ©semi.com</div>

</div>

</html>