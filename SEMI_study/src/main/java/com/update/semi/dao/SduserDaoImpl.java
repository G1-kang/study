package com.update.semi.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SduserDto;

@Repository
public class SduserDaoImpl implements SduserDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public SduserDto selectOne(String sdueamil) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
