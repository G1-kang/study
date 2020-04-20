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

//빈칸이 있었을 경우 정보가 넘어가지 않도록 함 
function vaildate(){
	
	var userid = document.getElementById("no_sduemail").value;
	var pwch = document.getElementById("no_sdupwch").value;
	var pw = document.getElementsById("no_sdupw").value;
	var name = document.getElementById("no_sduname").value;
	var nickname=document.getElementById("no_sdunick").value;
	var dob = document.getElementById("no_sdudob").value;
	var gender = document.getElementByName("no_sdusex")[0].value;
	//var month = document.getElementById("month");
	//var date = document.getElementById("date");

	if (pw != pwch) {
		alert("비밀번호가 틀립니다. 다시 한번 확인해 주세요");

		pwch = "";
		pwch.focus();
		return false;

	} else if (userid == "") {
		alert("사용하실 이아디를 입력해 주세요");

		userid.focus();
		return false;

	} else if (name == "") {
		alert("이름을 입력해 주세요");

		name.focus();
		return false;

	} else if (dob = "") {
		alert("생년월일을 입력해 주세요");

		dob.focus();
		return false;

	}else if(nickname == ""){
		alert("사용하실 닉네임을 입력해 주세요");

		nickname.focus();
		return false;

	}else if(gender == ""){
		alert("성별을 선택해 주세요");

	
		return false;

	}
	
	
}

//id중복검사 
function nomal_idChk() {
		
		var userid = document.getElementById("no_sduemail").value;//값이 안넘어옴
		console.log(no_userId+"아ㅇ디");
		if(no_userId == ""){
			alert('아이디를 입력하세요');	
			
			//return false;
			
		}else {
		
			$.ajax({
				url:"SduserRegistIdCheck.do",
				data:{
					command:"nomal_idChk",
					sduemail:userid
					},
				type:"post",
				datatype:"text",
				success:function(res){
					alert('통신성공'+res);
					if(res == "false"){
						alert("사용가능한 아이디 입니다");
					}else if(res == "true"){
						alert("이미 사용중 아이디 입니다");
					}
					
				},error:function(){
					alert('통신실패');
				}
			});
			
		}
		
}

//비밀번호 확인 
$(function () {
	
	console.log($('#no_sdupw').val());

	$("#pwConfirm").html("비밀번호 입력 해주세요");
	$("#pwConfirm").css('color','blue');
	
	
	$("#no_userPwCh").keyup(function () {
		var no_upw = $("#no_sdupw").val();
		var no_upwch = $("#no_sdupwch").val();
		
		if(no_upw != ""){
			console.log('비밀번호:'+$('#no_sdupw').val()+'비번확인'+$('#no_sdupwch').val());
			if(no_upw != no_upwch){
				$("#pwConfirm").html("비번이 틀렸습니다");
				$("#pwConfirm").css('color','red');
			}else if(no_upw == no_upwch){
				$("#pwConfirm").html("비번이 일치 합니다");
				$("#pwConfirm").css('color','green');
			}
			
		}
		
	});
	
});

</script>


</head>
<body>

	<div>
		<form action="SduserRegist.do" method="post" name="regist" onsubmit="return vaildate();">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" placeholder="aaa@naver.com으로 작성해주세요 " id="no_sduemail" name="sduemail" required="required">
						<input type="button" value="중복검사" onclick="nomal_idChk()">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="text" id="no_sdupw" name="sdupw" required="required">
					</td>
				</tr>
				<tr>
					<th>비밀번호재입력</th>
					<td>
						<input type="text"  id="no_sdupwch" name="sdupwch" required="required">
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
						<input type="radio" name="no_sdusex" checked="checked" value="MALE" /> MALE 
						<input type="radio" name="no_sdusex" value="FEMALE"  />FEMALE
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<input type="text" placeholder="20201201" id="no_sdudob" name="sdudob" required="required">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" id="no_sduname" name="sduname" required="required">
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						<input type="text" id="no_sdunick" name="sdunick" required="required">
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<input type="submit" value="회원가입" >
						<input type="button" value="취소" onclick="location.href='index.html'">
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>