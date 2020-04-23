package com.update.semi.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.update.semi.dao.SduserDao;
import com.update.semi.dto.SduserDto;

@Service
public class SduserBizImpl implements SduserBiz{

	@Autowired
	private SduserDao dao;

	@Override
	public SduserDto emailCheck(String sduemail) {
		System.out.println("biz222222 : " + dao.emailCheck(sduemail));
		return dao.emailCheck(sduemail);
	}

	@Override
	public int join(SduserDto dto) {
		
		return dao.join(dto);
	}

	@Override
	public SduserDto login(SduserDto dto) {
		return dao.login(dto);
	}


}
