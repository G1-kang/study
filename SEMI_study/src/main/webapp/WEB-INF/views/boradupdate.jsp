<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 부트스트랩없는 써머노트  -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/CSS/summernote-lite.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/JS/summernote/summernote-lite.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/JS/summernote/summernote-ko-KR.min.js"></script>

<script type="text/javascript">
$(function () {


	$('#summernote').summernote({
	    placeholder: 'Hello stand alone ui',
	    tabsize: 2,
	    height: 120,
	    width:650,
	    toolbar: [
	      ['style', ['style']],
	      ['font', ['bold', 'underline', 'clear']],
	      ['color', ['color']],
	      ['para', ['ul', 'ol', 'paragraph']],
	      ['table', ['table']],
	      ['insert', ['link', 'picture', 'video']],
	      ['view', ['fullscreen', 'codeview', 'help']]
	    ]
	  });
	
	$('#summernote').summernote('code','${updatedto.sdubcontent}');

	
});
</script>


</head>
<body>

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
글 보기 

<form action="boardupdateres.do" method="post">
		<p>
		<span>제목</span>
		<span><input type="text" value="${ updatedto.sdbtitle}" name="sdbtitle"></span>
		</p>
		<p>
		<span>날짜</span>
		<span>${updatedto.sdbregdate }</span>
		</p>
		<textarea id="summernote" name="sdubcontent"></textarea>
	
	
	<table border="1">
	<col width="150px">
	<col width="300px">

	<tr>
		<th>첨부 파일</th>
		<td>첨부파일 목록 ~</td>
		<td>
			<input type="hidden" name="sdbfilename" value=null>
			<input type="hidden" name="sdbfilpath" value=null>
			<input type="button" value="첨부하기">
		</td>
	</tr>
	<tr>
		<td colspan="3" >
			 이름 : <input type="text" size="50">
			<input type="button" value="댓글"/>

		</td>
	</tr>
</table>
		<input type="submit" value="수정하기"/>
		<input type="button" value="목록으로" onclick="location.href='boardlist.do'"/>
</form>
</div>
</div>

<div id="footer" style="background-color:#CE9FFC;clear:both;text-align:center;">
Copyright ©semi.com</div>

</div>


</body>
</html>