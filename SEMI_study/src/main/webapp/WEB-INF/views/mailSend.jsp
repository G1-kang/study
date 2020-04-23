<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<script type="text/javascript">
function sendmailbtn() {
	
	var sduemail = document.getElementsByName('sduemail')[0].value;
	
	$.ajax({
		url:"sendemail.do",
		data: {"sduemail":sduemail},
		type:"post",
		datatype:"text",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success:function(res){
			alert('통신성공'+res);
			if(res == "succ"){
				alert("인증 번호를 전송 했습니다");
// 			    ckeck1 = true;
// 				return ckeck1;
				
			}else if(res == "fail"){
				alert("인증번호 전송 실패 했습니다");
			}
			
		},error:function(){
			alert('통신실패');
		}
	});
	
}

</script>

</head>
<body>

<input type="text" placeholder="oooo@naver.com" name="sduemail">
<input type="button" value="이메일인증" onclick="sendmailbtn()">

</body>
</html>