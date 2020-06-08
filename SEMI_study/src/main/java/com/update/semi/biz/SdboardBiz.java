package com.update.semi.biz;

import java.util.List;

import com.update.semi.dto.SdboardDto;

public interface SdboardBiz {

	//모든 글 보기 
	public List<SdboardDto> list();
	//글 하나 보기 
	public SdboardDto selectOne(int sdbseq);
	//글 쓰기 
	public int write(SdboardDto sdboarddto);
	//글 수정 
	public int update(SdboardDto sdboarddto);
	//글 삭제 
	public int delete(int sdbseq); 
	
	//이미지 넣기 
	public int insertImg(SdboardDto sdboarddto);
	//이미지 넣고 보드 넘버를 가지고 오기 
	public String getBoardNo(SdboardDto sdboarddto);
	//db에 글추가 하기 
	public int insertNoImgBoard(SdboardDto sdboarddto);
	//db에 글 수정하기 
	public int updateRestContent(SdboardDto sdboarddto);
	
}
