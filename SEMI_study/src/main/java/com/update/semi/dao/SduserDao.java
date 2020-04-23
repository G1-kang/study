package com.update.semi.dao;

import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.update.semi.dto.SduserDto;

public interface SduserDao {
	
	String NAMESPACE = "com.update.semi.sduser.";
	//아이디 체크 
	public SduserDto emailCheck(String sduemail);
	//회원가입
	public int join(SduserDto dto);
	
	//로그인 
	public SduserDto login(SduserDto dto);
}
