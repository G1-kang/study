package com.update.semi.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.update.semi.dao.SduserDao;
import com.update.semi.dto.SduserDto;

@Service
public class SduserBizImpl implements SduserBiz{

	@Autowired
	private SduserDao dao;

	@Override
	public SduserDto selectOne(String sdueamil) {
		// TODO Auto-generated method stub
		return null;
	}
}
