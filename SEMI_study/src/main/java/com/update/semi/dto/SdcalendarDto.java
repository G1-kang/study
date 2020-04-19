package com.update.semi.dto;

import java.util.Date;

public class SdcalendarDto {

	  // 일정시퀀스 
    private int sdcseq;

    // 일정제목 
    private String sdctitle;

    // 일정내용 
    private String sdccontent;

    // 일정시작날짜 
    private Date sdcsartdate;

    // 일정끝날짜 
    private Date sdcenddate;

    // 아이디 일정 작성자
    private String sdueamil;

	public SdcalendarDto() {
		
	}

	public SdcalendarDto(int sdcseq, String sdctitle, String sdccontent, Date sdcsartdate, Date sdcenddate,
			String sdueamil) {
		super();
		this.sdcseq = sdcseq;
		this.sdctitle = sdctitle;
		this.sdccontent = sdccontent;
		this.sdcsartdate = sdcsartdate;
		this.sdcenddate = sdcenddate;
		this.sdueamil = sdueamil;
	}

	public int getSdcseq() {
		return sdcseq;
	}

	public void setSdcseq(int sdcseq) {
		this.sdcseq = sdcseq;
	}

	public String getSdctitle() {
		return sdctitle;
	}

	public void setSdctitle(String sdctitle) {
		this.sdctitle = sdctitle;
	}

	public String getSdccontent() {
		return sdccontent;
	}

	public void setSdccontent(String sdccontent) {
		this.sdccontent = sdccontent;
	}

	public Date getSdcsartdate() {
		return sdcsartdate;
	}

	public void setSdcsartdate(Date sdcsartdate) {
		this.sdcsartdate = sdcsartdate;
	}

	public Date getSdcenddate() {
		return sdcenddate;
	}

	public void setSdcenddate(Date sdcenddate) {
		this.sdcenddate = sdcenddate;
	}

	public String getSdueamil() {
		return sdueamil;
	}

	public void setSdueamil(String sdueamil) {
		this.sdueamil = sdueamil;
	}

	@Override
	public String toString() {
		return "SdcalendarDto [sdcseq=" + sdcseq + ", sdctitle=" + sdctitle + ", sdccontent=" + sdccontent
				+ ", sdcsartdate=" + sdcsartdate + ", sdcenddate=" + sdcenddate + ", sdueamil=" + sdueamil + "]";
	}
	
	
    
    
    
}
