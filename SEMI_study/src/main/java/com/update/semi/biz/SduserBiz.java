package com.update.semi.biz;

import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.update.semi.dto.SduserDto;

public interface SduserBiz {

	public SduserDto emailCheck(String sduemail);
	//회원가입 - sns포함 
	public int join(SduserDto dto);
	
	//로그인 
	public SduserDto login(SduserDto dto);
	
	//아이디 회원가입 되어있는지 확인 하기 위해 정보 갖고오기 
	public SduserDto snsemailchk(String sduemail);
	
	//자기 정보 수정 
	public int updatemypage(SduserDto dto);
	
	//정보 수정한뒤 정보 뽑아 올 때 
	public SduserDto selectOne(String sduemail);
		

}
