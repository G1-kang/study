<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 제이쿼리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.min.js"></script>
<!-- ${pageContext.request.contextPath} -->
<script type="text/javascript">
var ckeck1 = false;//id유효성검사

function nn() {
	var gender = document.getElementsByName("sdusex");


	for(var i=0; i<gender.length; i++) {
    	if(gender[i].checked) {
    		genderval = gender[i].value;
    		alert(genderval);
    		return genderval;
    	}
	}
	
// 	var userid = document.getElementById("no_sduemail").value;
// 	var pwch = document.getElementById("no_sdupwch").value;
// 	var pw = document.getElementById("no_sdupw").value;
// 	var name = document.getElementById("no_sduname").value;
// 	var nickname=document.getElementById("no_sdunick").value;
// 	var dob = document.getElementById("no_sdudob").value;
	
// 	console.log(userid+pw+name+nickname+dob+genderval+ckeck1);
// 	 if (name == "") {
// 		alert("이름을 입력해 주세요");
// 		return false;

// 	}else if(pw==""||pwch=="" ||pw != pwch){
// 		alert("비번 불일치 재입력");
// 		return false;
// 	}else if(nickname == ""){
// 		alert("빈칸임");
// 		return false;
// 	}else if(dob == ""){
// 		alert("생년월일 쓰셈");
// 		return false;
// 	}else if(ckeck1 == false){
// 		alert("아이디 중복 검사를 해주세요 ");
// 		return false;
// 	}
	
}


//빈칸이 있었을 경우 정보가 넘어가지 않도록 함 
function vaildate(){
	
	var sdusex = nn();
	var userid = document.getElementById("no_sduemail").value;
	var pwch = document.getElementById("no_sdupwch").value;
	var pw = document.getElementById("no_sdupw").value;
	var name = document.getElementById("no_sduname").value;
	var nickname=document.getElementById("no_sdunick").value;
	var dob = document.getElementById("no_sdudob").value;
	
	console.log(userid+pw+name+nickname+dob+sdusex+ckeck1);//출력됨 ㅇㅇ 근데 genderval안나옴
	 if (name == "") {
		alert("이름을 입력해 주세요");
		return false;

	}else if(pw==""||pwch=="" ||pw != pwch){
		alert("비밀 번호를 입력을 다시 해주세요 ");
		return false;
	}else if(nickname == ""){
		alert("닉네임을 입력해 주세요 ");
		return false;
	}else if(dob == ""){
		alert("생년월일을 입력해 주세요 ");
		return false;
	}else if(ckeck1 == false){
		alert("아이디 중복 검사를 해주세요 ");
		return false;
	}
	
}

//id중복검사 
function nomal_idChk() {
		
		var userid = document.getElementById("no_sduemail").value;
		console.log(userid+"아ㅇ디");
		if(userid == ""){
			alert('아이디를 입력하세요');	
			
			//return false;
			
		}else {
			let sduemail ={"sduemail":userid}
			//post방식으로넣어 줬을 때 controller에 가지 않을 수 있어서 한번더 담아서 보내 준다 
			
			$.ajax({
				url:"SduserRegistIdCheck.do",
				data: sduemail,
				type:"post",
				datatype:"text",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success:function(res){
					//alert('통신성공'+res);
					if(res == "true"){
						alert("사용가능한 아이디 입니다");
					    ckeck1 = true;
						return ckeck1;
						
					}else if(res == "false"){
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
	
	
	$("#no_sdupwch").keyup(function () {

		var no_upw = $("#no_sdupw").val();
		var no_upwch = $("#no_sdupwch").val();
		
		if(no_upw != ""){
			console.log('비밀번호:'+no_upw +'비번확인'+no_upwch);
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
						<input type="text" placeholder="aaa@naver.com으로 작성해주세요 " id="no_sduemail" name="sduemail">
						<input type="button" value="중복검사" onclick="nomal_idChk()">
						<input type="button" value="이메일 인증 " onclick="location.href='mailsendpage.do'">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="text" id="no_sdupw" name="sdupw" >
					</td>
				</tr>
				<tr>
					<th>비밀번호재입력</th>
					<td>
						<input type="text"  id="no_sdupwch" name="sdupwch" >
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
						<input type="radio" name="sdusex" checked="checked" value="M" /> 남자 
						<input type="radio" name="sdusex" value="F" />여자
						<input type="button" value="확인" onclick="nn()">
					
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<input type="text" placeholder="20201201" id="no_sdudob" name="sdudob">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" id="no_sduname" name="sduname" >
					</td>
				</tr>
				<tr>
					<th>별명</th>
					<td>
						<input type="text" id="no_sdunick" name="sdunick">
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<input type="submit" value="회원가입" >
						<input type="button" value="취소" onclick="location.href='main.do'">
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>