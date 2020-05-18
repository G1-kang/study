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
	
}
