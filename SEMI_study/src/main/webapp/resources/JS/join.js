var ckeck1 = false;//id유효성검사

//성별 라디오박스에서 뽑아 오기 
function innerVal() {
	var gender = document.getElementsByName("sdusex");


	for(var i=0; i<gender.length; i++) {
    	if(gender[i].checked) {
    		genderval = gender[i].value;
    		alert(genderval);
    		return genderval;
    	}
	}

	
}

//이메일 형식 검사 
$(function () {

	/*
	 var regExp = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i";
	 * 
	else if(regExp.test(userid)==false){
		alert("이메일 형식이 올바르지 않습니다. ");
		return false;
	}
	*/
	$("#no_sduemail").keyup(function () {

		var no_eamil = $("#no_sduemail").val();
		//자바스크립트에서 
		//정규표현식을 쓸때 "", ''없이 사용하거나 
		//new RegExp(" ~~~~ ") 이렇게 써야함 
		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		console.log('이메일 키업의 값 '+no_eamil);
		
		if(!regExp.test(no_eamil)){
			$("#emailConfirm").html("이메일 형식에 맞게 입력 해주세요");
			$("#emailConfirm").css('color','red');
		}else if(regExp.test(no_eamil)){
			$("#emailConfirm").html("");
		}
		
	});
	
});

//빈칸이 있었을 경우 정보가 넘어가지 않도록 함 
function vaildate(){
	
	var sdusex = innerVal();
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