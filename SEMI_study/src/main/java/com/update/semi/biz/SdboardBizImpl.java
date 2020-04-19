package com.update.semi.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.update.semi.dao.SdboardDao;

@Service
public class SdboardBizImpl implements SdboardBiz{

	@Autowired
	private SdboardDao dao;
	
}
