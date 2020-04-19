package com.update.semi.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.update.semi.dao.SdcalendarDao;

@Service
public class SdcalendarBizImpl implements SdcalendarBiz{

	@Autowired
	private SdcalendarDao dao;
}
