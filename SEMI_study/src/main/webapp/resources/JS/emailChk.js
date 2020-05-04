


//모달 팝업 이메일 인증 
function modalEmailAuth(){

	
	var str ="";
		
	str += "<table>";
	str += "<tr>";
	str += "<td><input type='text' placeholder='oooo@naver.com' id='echkeamil' name='sduemail'></td><td><input type='button' value='이메일인증' onclick='sendmailbtn()'></td>";
	str += "</tr>";
	str += "<tr>";
	str += "<td>"+
		"<input type='text' placeholder='인증번호를 입력해주세요' id='echAuthnochk' name='AuthNoChk'>"+
	"</td>"+
	"<td>"+
		"<input type='button' value='인증번호 확인' onclick='AuthNoChkbtn()'>"+
	"</td>";
	str += "</tr></table>";
	str += "<div style='cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;' onClick='close_pop();'>"
    +"<span class='pop_bt' style='font-size: 13pt;'>닫기</span></div>";

    document.getElementById("modal-content").innerHTML=str;
	 $('#myModal').show();
	
}




//팝업 Close 기능
function close_pop(flag) {
	 $('#myModal').hide();
	}

//이메일 인증 번호 전송 
function sendmailbtn() {
	
	var sduemail = document.getElementById('echkeamil').value;
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

//인증번호 확인 
function AuthNoChkbtn() {
	
	var AuthNoChk = document.getElementById('echAuthnochk').value;
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