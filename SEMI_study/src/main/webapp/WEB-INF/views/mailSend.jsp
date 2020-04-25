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
	if(sduemail == ""){
		alert("이메일을 입력해 주세요 ");
	}else{
	

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
//	 			    ckeck1 = true;
//	 				return ckeck1;
					
				}else if(res == "fail"){
					alert("인증번호 전송 실패 했습니다");
				}
				
			},error:function(){
				alert('통신실패');
			}
		});
		
	}
	
	
}


function AuthNoChkbtn() {
	
	var AuthNoChk = document.getElementsByName("AuthNoChk")[0].value;
	if(AuthNoChk == ""){
		alert("인증번호를 입력해 주세요");
	}else{
		$.ajax({
			url:"AuthNoChk.do",
			method:"post",
			dataType:"text",
			data:{"AuthNoChk":AuthNoChk},
			success:function(res){
				alert("통신성공");
				if(res == "succ"){
					alert("인증번호 확인 완료");
				}else if(res == "fail"){
					alert("인증번호 확인 실패"+res);
				}
				
			},error:function(){
				alert("통신실패");
			}
			
		});	
	}
	
}

</script>

</head>
<body>

<table>
	<tr>
		<td>
			<input type="text" placeholder="oooo@naver.com" name="sduemail">
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="이메일인증" onclick="sendmailbtn()">
		</td>
	</tr>
	<tr>
		<td>
			<input type="text" placeholder="인증번호를 입력해주세요" name="AuthNoChk">
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="인증번호 확인" onclick="AuthNoChkbtn()">
		</td>
	</tr>
	
</table>

</body>
</html>