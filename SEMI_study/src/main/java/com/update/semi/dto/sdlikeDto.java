package com.update.semi.dto;

public class sdlikeDto {
	
	//좋아요 개수
	private int sdlikeseq; 
	
	//좋아요 번호 
	private int sdbseq; 
	
	//좋아요한 글 제목 
	private String sdbtitle; 
	
	//좋아요 누른 사람의 아이디 
	private String sduemail;

	public sdlikeDto() {

	}

	public sdlikeDto(int sdlikeseq, int sdbseq, String sdbtitle, String sduemail) {
		this.sdlikeseq = sdlikeseq;
		this.sdbseq = sdbseq;
		this.sdbtitle = sdbtitle;
		this.sduemail = sduemail;
	}

	public int getSdlikeseq() {
		return sdlikeseq;
	}

	public void setSdlikeseq(int sdlikeseq) {
		this.sdlikeseq = sdlikeseq;
	}

	public int getSdbseq() {
		return sdbseq;
	}

	public void setSdbseq(int sdbseq) {
		this.sdbseq = sdbseq;
	}

	public String getSdbtitle() {
		return sdbtitle;
	}

	public void setSdbtitle(String sdbtitle) {
		this.sdbtitle = sdbtitle;
	}

	public String getSduemail() {
		return sduemail;
	}

	public void setSduemail(String sduemail) {
		this.sduemail = sduemail;
	}

	@Override
	public String toString() {
		return "sdlikeDto [sdlikeseq=" + sdlikeseq + ", sdbseq=" + sdbseq + ", sdbtitle=" + sdbtitle + ", sduemail="
				+ sduemail + "]";
	} 
	
	
	
	
	
	

}
