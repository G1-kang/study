package com.update.semi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String sendmail(@ModelAttribute("sduemail") String sduemail) {
		
		System.out.println("메일 보내기 컨트롤러 : "+sduemail);
		
		try {
			smtpDto.sendMail(sduemail, "인증번호 전송 ", "인증번호 입니다");
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		} 
		
		//첨부되어서 전송은 되지만, 첨부주소에 접속이 불가능하다고 나온다 수정해야할 사항 !!!C:\\Users\\강지원\\Desktop, a.jpg라고 적었을시에 
//		try {
//			smtpDto.sendMail(sduemail, "이것은 제목", "스프링으로 구현해서 보내본다.","C:\\Users\\강지원\\Desktop\\a.jpg","파일이름.txt");//a.jpg
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "fail";			
//		}
		
		
		return "succ";
	}
	
	
}
