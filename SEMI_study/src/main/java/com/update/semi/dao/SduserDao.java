package com.update.semi.dao;

import java.util.Map;

import com.update.semi.dto.SduserDto;

public interface SduserDao {
	
	String NAMESPACE = "sduser.";
	
	public SduserDto emailCheck(String sduemail);
	public int join(SduserDto dto);
}
