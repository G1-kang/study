package com.update.semi.dto;

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class SdboardDto {

	    // 글시퀀스 
	    private int sdbseq;

	    // 탭번호 
	    private int sdbtabno;

	    // 그룹시퀀스 
	    private int sdbgroupseq;

	    // 그룹번호 
	    private int sdbgroupno;

	    // 글제목 
	    private String sdbtitle;

	    // 아이디 글 작성자
	    private String sduemail;

	    // 글내용 
	    private String sdubcontent;

	    // 글작성일 
	    private Date sdbregdate;

	    // 조회수 
	    private int sdbviews;

	    // 좋아요 
	    private int sdblike;

	    // 파일이름 
	    private String sdbfilename;

	    // 파일경로 
	    private String sdbfilpath;

	    
	    //이미지 파일 경로
	    private String sdbimgpath;
	    
	    //닉네임 
	    private String sdunick;
	    
	    //파일 배열 로 받기 
	    private MultipartFile[] file;
	    
	    //썸네일 이미지 받아오기 
	    private String thumbnail;
	    
		public SdboardDto() {
			
		}



		public SdboardDto(int sdbseq, int sdbtabno, int sdbgroupseq, int sdbgroupno, String sdbtitle, String sduemail,
				String sdubcontent, Date sdbregdate, int sdbviews, int sdblike, String sdbfilpath,
				 String sdbimgpath, String sdunick, MultipartFile[] file, String thumbnail) {
			super();
			this.sdbseq = sdbseq;
			this.sdbtabno = sdbtabno;
			this.sdbgroupseq = sdbgroupseq;
			this.sdbgroupno = sdbgroupno;
			this.sdbtitle = sdbtitle;
			this.sduemail = sduemail;
			this.sdubcontent = sdubcontent;
			this.sdbregdate = sdbregdate;
			this.sdbviews = sdbviews;
			this.sdblike = sdblike;
			this.sdbfilpath = sdbfilpath;
			this.sdbimgpath = sdbimgpath;
			this.sdunick = sdunick;
			this.file = file;
			this.thumbnail = thumbnail;
		}




		public String getSdubcontent() {
			return sdubcontent;
		}



		public MultipartFile[] getFile() {
			return file;
		}

		public void setFile(MultipartFile[] file) {
			this.file = file;
		}

		public void setSdubcontent(String sdubcontent) {
			this.sdubcontent = sdubcontent;
		}



		public String getSdbimgpath() {
			return sdbimgpath;
		}



		public void setSdbimgpath(String sdbimgpath) {
			this.sdbimgpath = sdbimgpath;
		}



		public int getSdbseq() {
			return sdbseq;
		}

		public void setSdbseq(int sdbseq) {
			this.sdbseq = sdbseq;
		}

		public int getSdbtabno() {
			return sdbtabno;
		}

		public void setSdbtabno(int sdbtabno) {
			this.sdbtabno = sdbtabno;
		}

		public int getSdbgroupseq() {
			return sdbgroupseq;
		}

		public void setSdbgroupseq(int sdbgroupseq) {
			this.sdbgroupseq = sdbgroupseq;
		}

		public int getSdbgroupno() {
			return sdbgroupno;
		}

		public void setSdbgroupno(int sdbgroupno) {
			this.sdbgroupno = sdbgroupno;
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



		public Date getSdbregdate() {
			return sdbregdate;
		}

		public void setSdbregdate(Date sdbregdate) {
			this.sdbregdate = sdbregdate;
		}

		public int getSdbviews() {
			return sdbviews;
		}

		public void setSdbviews(int sdbviews) {
			this.sdbviews = sdbviews;
		}

		public int getSdblike() {
			return sdblike;
		}

		public void setSdblike(int sdblike) {
			this.sdblike = sdblike;
		}


		public String getSdbfilpath() {
			return sdbfilpath;
		}

		public void setSdbfilpath(String sdbfilpath) {
			this.sdbfilpath = sdbfilpath;
		}



		public String getSdunick() {
			return sdunick;
		}

		public void setSdunick(String sdunick) {
			this.sdunick = sdunick;
		}
		
		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		@Override
		public String toString() {
			return "SdboardDto [sdbseq=" + sdbseq + ", sdbtabno=" + sdbtabno + ", sdbgroupseq=" + sdbgroupseq
					+ ", sdbgroupno=" + sdbgroupno + ", sdbtitle=" + sdbtitle + ", sduemail=" + sduemail
					+ ", sdubcontent=" + sdubcontent + ", sdbregdate=" + sdbregdate + ", sdbviews=" + sdbviews
					+ ", sdblike=" + sdblike + ", sdbfilpath=" + sdbfilpath
					 + ", sdbimgpath=" + sdbimgpath + ", sdunick=" + sdunick + ", file="
					+ Arrays.toString(file) + ", thumbnail=" + thumbnail + "]";
		}

		
	   
	
	
	
}
