package com.update.semi.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.update.semi.dto.SMTPDto;

@Controller
public class SMTPController {

	@Autowired
	private SMTPDto smtpDto; 
	
	@RequestMapping(value = "/mailsendpage.do" , method=RequestMethod.GET)
	public String sendemailpage() {
		return "mailSend";
	}
	
	@RequestMapping(value = "/sendemail.do", method=RequestMethod.POST)
	@ResponseBody
	protected String sendmail(@ModelAttribute("sduemail") String sduemail, HttpServletRequest requset) throws Exception {
		
		System.out.println("메일 보내기 컨트롤러 : "+sduemail);
		
		String temp = smtpDto.createAuthNo().toString().trim();//랜덤번호 생성 후 담아줌 
		System.out.println("smtp 랜덤 번호 : "+temp);
		
		try {
			smtpDto.sendAuthNo(sduemail, temp); //만들어둔 dto의 메서드에 값을 주입 : 수신자에게 번호 전송
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
		HttpSession AuthNo = requset.getSession();
		AuthNo.setAttribute("AuthNo", temp); //session에 값을 담아서 클라가 적은 값과 비교하기 위해 전송하자  
		
		/*//이메일만 보내기 
		try {
			smtpDto.sendMail(sduemail, "인증번호 전송 ", "인증번호 입니다");
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		} 
		*/
		
		//첨부파일 보내기 
		//첨부되어서 전송은 되지만, 첨부주소에 접속이 불가능하다고 나온다 수정해야할 사항 !!!C:\\Users\\강지원\\Desktop, a.jpg라고 적었을시에 
//		try {
//			smtpDto.sendMail(sduemail, "이것은 제목", "스프링으로 구현해서 보내본다.","C:\\Users\\강지원\\Desktop\\a.jpg","파일이름.txt");//a.jpg
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "fail";			
//		}
		
		
		return "succ";
	}
	
	@RequestMapping(value = "/AuthNoChk.do", method = RequestMethod.POST)
	@ResponseBody
	protected String AuthNoChk(@ModelAttribute("AuthNoChk") String AuthNoChk ,HttpServletRequest req) {
		
		String AuthNo = (String)req.getSession().getAttribute("AuthNo");//session으로 담아뒀던 인증번호를 가지고옴
		
		//view에서 받아온 인증번호와 발송한 인증번호가 같은지 비교함 
		if(AuthNoChk.equals(AuthNo)) {
			return "succ";
		}
		
		return "fail";
	}
	
	
	
	
}
