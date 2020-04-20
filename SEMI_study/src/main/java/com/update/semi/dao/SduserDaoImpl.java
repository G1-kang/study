package com.update.semi.dao;

import java.util.Map;

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
	
	
	//아이디 중복 체크 
	@Override
	public SduserDto emailCheck(String sduemail) {

		SduserDto res = sqlSession.selectOne(NAMESPACE+"emailCheck",sduemail);
		return res;
	}

	//로그인 
	@Override
	public int join(SduserDto dto) {
		
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE+"join",dto);
		} catch (Exception e) {
			System.out.println("에러 - 회원가입 - 다오");
			e.printStackTrace();
		}
		
		return  res;
	}

	
}
