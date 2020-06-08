<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv='Content-type' content='text/html; charset=utf-8'>
<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">

<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/CSS/board/board.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/JS/board.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


</head>
<body>
<input type="hidden" name="sdbseq" value="${boardone.sdbseq }" id="sdbseq">
<input type="hidden" name="sduemail" value="${boardone.sduemail }">


<div id="container" style="width:100%">

<div id="header" style="background-color:#CE9FFC;">
<h1 style="margin-bottom:0;">Main Title of Web Page</h1></div>


<div id="menu">
	<div style="margin-left: 20px; margin-top: 10px; width: 100%">
		<a href="boardlist.do">전체 글</a><br>
	</div>
</div>

<div id="content">
	<div id="content_body">
		<div id="content_top">
		
		  <!-- 모달 영역 -->
   <div class="modal fade" id="boardModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                  aria-label="Close">
                  <span aria-hidden="true">&times;</span>
               </button>
               <h4 class="modal-title" id="myModalLabel">첨부 파일 리스트</h4>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
               <button type="button" class="btn btn-primary" id="yes-btn"
                  onclick="fileDownChk();">선택 다운로드</button>
               <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
            </div>
         </div>
      </div>
   </div>
		
		
		
		글 보기 
				<p>
					<span>작성자</span>
					<span>${boardone.sdunick }</span>
				</p>
				<p>
				<span>제목</span>
				<span>${ boardone.sdbtitle}</span>
				</p>
				<p>
				<span>날짜</span>
				<span>${boardone.sdbregdate }</span>
				</p>
		</div>		
		<div id="content_btm">	
			<table border="1">
				<col width="150px">
				<col width="300px">
				<tr>
				<td align="center" colspan="3">
					<div id="sdubcontent">
						${boardone.sdubcontent }
					</div>
				</td>
				</tr>
				<tr>
					<th>첨부 파일</th>
					<td>
						<c:if test="${empty  boardone.sdbfilpath}">
						첨부파일 없음 
						</c:if>
						<c:if test="${not empty  boardone.sdbfilpath}">
						${boardone.sdbfilpath}
						</c:if>						
					</td>
					<td>
						<input type="button" value="첨부파일확인" onclick="fileDetail('${boardone.sdbseq }');">
					</td>
				</tr>
				<tr>
					<td colspan="3" >
						 ${ boardone.sdunick} : <input type="text" size="50">
						<input type="button" value="댓글"/>
			
					</td>
				</tr>
			</table>
				<input type="button" value="수정하기" onclick="location.href='boardupdate.do?sdbseq=${boardone.sdbseq}'"/>
				<input type="button" value="글쓰기" onclick="location.href='boardwrite.do'"/>
				<input type="button" value="목록으로" onclick="location.href='boardlist.do'"/>
				<input type="button" value="삭제" onclick="location.href='boarddelete.do?sdbseq=${boardone.sdbseq }'">
		</div>
	</div>
<footer id="footer">
Copyright ©semi.com
</footer>
</div>



</div>




</body>
</body>
</html>