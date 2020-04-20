package com.update.semi.biz;

import java.util.Map;

import com.update.semi.dto.SduserDto;

public interface SduserBiz {

	public SduserDto emailCheck(String sduemail);
	
	public int join(SduserDto dto);
}
