package com.update.semi.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.update.semi.dao.SdboardDao;
import com.update.semi.dto.SdboardDto;

@Service
public class SdboardBizImpl implements SdboardBiz{
	
	private Logger logger = LoggerFactory.getLogger(SdboardBizImpl.class); 
	
	@Autowired
	private SdboardDao dao;

	@Override
	public List<SdboardDto> list() {
		logger.info("게시판 전체 리스트 Biz 확인 : >>>>>>>>>>>>>>>>>>>> ");
		return dao.list();
	}

	@Override
	public SdboardDto selectOne(int sdbseq) {
		return dao.selectOne(sdbseq);
	}

	@Override
	public int write(SdboardDto sdboarddto) {
		return dao.write(sdboarddto);
	}

	@Override
	public int update(SdboardDto sdboarddto) {
		return dao.update(sdboarddto);
	}

	@Override
	public int delete(int sdbseq) {
		return dao.delete(sdbseq);
	}

	@Override
	public int insertImg(SdboardDto sdboarddto) {
		return dao.insertImg(sdboarddto);
				
	}

	@Override
	public String getBoardNo(SdboardDto sdboarddto) {
		String sdbseq = Integer.toString(dao.getBoardNo(sdboarddto));
		return sdbseq;
	}

	@Override
	public int insertNoImgBoard(SdboardDto sdboarddto) {
		System.out.println("글 추가 Biz >>>>>>>>>>>>>>>>> " + sdboarddto);
		return dao.insertNoImgBoard(sdboarddto);
	}

	@Override
	public int updateRestContent(SdboardDto sdboarddto) {
		return dao.updateRestContent(sdboarddto);
	}

	@Override
	public int getTotalBoard(SdboardDto sdboarddto) {
		logger.info("페이징 페이지 수  biz "+sdboarddto);
		return dao.getTotalBoard(sdboarddto);
	}

	@Override
	public List<SdboardDto> boardList(SdboardDto sdboarddto) {
		logger.info("페이징 list biz "+sdboarddto);
		return dao.boardList(sdboarddto);
	}

	@Override
	public int updateImg(SdboardDto sdboardDto) {
		logger.info("이미지 업데이트 biz dto:"+sdboardDto);
		return dao.updateImg(sdboardDto);
	}

	@Override
	public int updateNoImgBoard(SdboardDto sdboardDto) {
		logger.info("updateNoImgBoard biz sdboardDto :"+sdboardDto);
		return dao.updateNoImgBoard(sdboardDto);
	}

	@Override
	public int updateBoard(SdboardDto sdboardDto) {
		logger.info("updateBoard biz :"+sdboardDto);
		return dao.updateBoard(sdboardDto);
	}
	
}
