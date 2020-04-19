package com.update.semi.dao;

import com.update.semi.dto.SduserDto;

public interface SduserDao {
	
	String NAMESPACE = "sduser.";
	
	public SduserDto selectOne(String sdueamil);
}
