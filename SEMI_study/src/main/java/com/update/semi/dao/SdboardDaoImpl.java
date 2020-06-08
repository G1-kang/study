package com.update.semi.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.update.semi.dto.SdboardDto;

@Repository
public class SdboardDaoImpl implements SdboardDao{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	Logger logger = LoggerFactory.getLogger(SdboardDaoImpl.class);
	
	@Override
	public List<SdboardDto> list() {
		List<SdboardDto> list = null; 
		list = sqlSession.selectList(NAMESPACE+"selectList");
		logger.info("게시판 전체 리스트 Dao 확인 : >>>>>>>>>>>>>>>>>>>> " + list);
		return list;
		
		
	}

	@Override
	public SdboardDto selectOne(int sdbseq) {
		
		SdboardDto sdboarddto = null; 
		sdboarddto = sqlSession.selectOne(NAMESPACE+"selectOne", sdbseq);
		return sdboarddto;
		
	}

	@Override
	public int write(SdboardDto sdboarddto) {
		
		int res = 0; 
		res = sqlSession.insert(NAMESPACE+"insertwrite", sdboarddto);
		
		return 0;
	}

	@Override
	public int update(SdboardDto sdboarddto) {
		
		int res = 0;
		res = sqlSession.update(NAMESPACE+"updatewrite",sdboarddto);
		return res;
	}

	@Override
	public int delete(int sdbseq) {
		int res= 0; 
		res = sqlSession.delete(NAMESPACE+"deletewrite", sdbseq);
		
		return res;
	}

	//이미지 넣기
	@Override
	public int insertImg(SdboardDto sdboarddto) {
		int res = 0; 
		res = sqlSession.insert(NAMESPACE+"insertImg",sdboarddto);
		return res;
	}

	//이미지 넣고 보드 넘버를 가지고 오기
	@Override
	public int getBoardNo(SdboardDto sdboarddto) {
		int sdbseq = 0; 
		sdbseq = sqlSession.selectOne(NAMESPACE+"getBoardNo",sdboarddto);
		return sdbseq;
	}

	//db에 글추가 하기
	@Override
	public int insertNoImgBoard(SdboardDto sdboarddto) {
		System.out.println("글 추가 bao >>>>>>>>>>>>>>>>> " + sdboarddto);
		int res =0 ; 
		res = sqlSession.insert(NAMESPACE+"insertNoImgBoard",sdboarddto);
		
		return res;
	}

	//db에 글 수정하기
	@Override
	public int updateRestContent(SdboardDto sdboarddto) {
		int res = 0; 
		res = sqlSession.update(NAMESPACE+"updateRestContent",sdboarddto);
		return res;
	}
	

}
