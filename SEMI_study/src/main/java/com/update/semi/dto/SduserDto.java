package com.update.semi.dto;

import java.io.File;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class SduserDto {
	
	// 유저시퀀스 
    private int sduseq;

    // 아이디
   // @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "아이디는 이메일 형식으로 적어주세요")
    private String sduemail;

    // 비번 : 숫자, 특문 각 1회 이상, 영문은 2개 이상 사용하여 8자리 이상 입력
   // @Pattern(regexp = "(?=.*\\d{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$", message = "숫자, 특문 각 1회 이상, 영문은 2개 이상 사용하여 8자리 이상 입력해주세요")
    private String sdupw;

    // 이름 
    private String sduname;

    // 성별 
    @NotEmpty(message="하나이상 체크해 주세요")
    @Pattern(regexp ="^(F)|(M)$", message = "성별 하나만 설정해 주세요")
    private String sdusex;

    // 닉네임 : 한글과 영문만 가능 
    @NotEmpty(message="사용하실 닉네임을 적어 주세요")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "한글과 영문만 가능 합니다")
    private String sdunick;

    // 생년월일 :yyyymmdd 형태로
    @Pattern(regexp = "^(19[0-9][0-9]|20[0-9][0-9])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$", message ="20101015의 형식으로 작성해 주세요 ")
    private String sdudob;

    // 탈퇴여부 
    private String sdudeact;

    // 회원등급 
    private String sdugrade;

    // 탈퇴날짜 
    private Date sdudeactdate;

    // 가입날짜 
    private Date sduregdate;
    
    //회원 이미지 
    private String sduimgpath;

    //파일 
    private MultipartFile userImgFile;

    
	public SduserDto() {
		
	}

	public SduserDto(int sduseq, String sduemail, String sdupw, String sduname, String sdusex, String sdunick,
			String sdudob, String sdudeact, String sdugrade, Date sdudeactdate, Date sduregdate,String sduimgpath,MultipartFile userImgFile) {
		super();
		this.sduseq = sduseq;
		this.sduemail = sduemail;
		this.sdupw = sdupw;
		this.sduname = sduname;
		this.sdusex = sdusex;
		this.sdunick = sdunick;
		this.sdudob = sdudob;
		this.sdudeact = sdudeact;
		this.sdugrade = sdugrade;
		this.sdudeactdate = sdudeactdate;
		this.sduregdate = sduregdate;
		this.sduimgpath = sduimgpath;
		this.userImgFile = userImgFile;
	}

	public int getSduseq() {
		return sduseq;
	}

	public void setSduseq(int sduseq) {
		this.sduseq = sduseq;
	}

	public String getSduemail() {
		return sduemail;
	}

	public void setSduemail(String sduemail) {
		this.sduemail = sduemail;
	}

	public String getSdupw() {
		return sdupw;
	}

	public void setSdupw(String sdupw) {
		this.sdupw = sdupw;
	}

	public String getSduname() {
		return sduname;
	}

	public void setSduname(String sduname) {
		this.sduname = sduname;
	}

	public String getSdusex() {
		return sdusex;
	}

	public void setSdusex(String sdusex) {
		this.sdusex = sdusex;
	}

	public String getSdunick() {
		return sdunick;
	}

	public void setSdunick(String sdunick) {
		this.sdunick = sdunick;
	}

	public String getSdudob() {
		return sdudob;
	}

	public void setSdudob(String sdudob) {
		this.sdudob = sdudob;
	}

	public String getSdudeact() {
		return sdudeact;
	}

	public void setSdudeact(String sdudeact) {
		this.sdudeact = sdudeact;
	}

	public String getSdugrade() {
		return sdugrade;
	}

	public void setSdugrade(String sdugrade) {
		this.sdugrade = sdugrade;
	}

	public Date getSdudeactdate() {
		return sdudeactdate;
	}

	public void setSdudeactdate(Date sdudeactdate) {
		this.sdudeactdate = sdudeactdate;
	}

	public Date getSduregdate() {
		return sduregdate;
	}

	public void setSduregdate(Date sduregdate) {
		this.sduregdate = sduregdate;
	}
	

	public String getSduimgpath() {
		return sduimgpath;
	}

	public void setSduimgpath(String sduimgpath) {
		this.sduimgpath = sduimgpath;
	}

	public MultipartFile getUserImgFile() {
		return userImgFile;
	}

	public void setUserImgFile(MultipartFile userImgFile) {
		this.userImgFile = userImgFile;
	}

	@Override
	public String toString() {
		return "SduserDto [sduseq=" + sduseq + ", sduemail=" + sduemail + ", sdupw=" + sdupw + ", sduname=" + sduname
				+ ", sdusex=" + sdusex + ", sdunick=" + sdunick + ", sdudob=" + sdudob + ", sdudeact=" + sdudeact
				+ ", sdugrade=" + sdugrade + ", sdudeactdate=" + sdudeactdate + ", sduregdate=" + sduregdate
				+ ", sduimgpath=" + sduimgpath + ", userImgFile=" + userImgFile + "]";
	}
	
	
    
    
    
    
}
