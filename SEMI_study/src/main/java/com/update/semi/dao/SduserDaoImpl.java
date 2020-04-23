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
		SduserDto res = null;
		System.out.println("여기는 다오임플 1"+sduemail);//값이 들어옴 
		
		res = sqlSession.selectOne(NAMESPACE+"emailCheck",sduemail);
		System.out.println("여기는 다오 임플 "+res);
		
		if(res == null) {
			res = new SduserDto();
		}
		System.out.println("여기는 다오 임플2222 확ㅇ안해보자 다오 임플 :"+res);
		return res;
	}

	//회원가입 
	@Override
	public int join(SduserDto dto) {
		System.out.println("여기는 회원가입 다오 : "+dto);
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE+"join",dto);
		} catch (Exception e) {
			System.out.println("에러 - 회원가입 - 다오");
			e.printStackTrace();
		}
		return  res;
	}

	//로그인
	@Override
	public SduserDto login(SduserDto dto) {
		
		SduserDto logindto = null;
		logindto = sqlSession.selectOne(NAMESPACE+"login",dto);
		
		return logindto;
	}


	
}
