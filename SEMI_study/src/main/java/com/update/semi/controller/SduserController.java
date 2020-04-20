package com.update.semi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SduserDto;

@Controller
public class SduserController {
	
	private Logger logger = LoggerFactory.getLogger(SduserController.class);
	
	@Autowired
	private SduserBiz sduserbiz;
	
	//회원가입 페이지로 
	@RequestMapping(value="/joinform.do", method = RequestMethod.GET)
	public String join(Model model) {
		return "RegistForm";
	}
	
	
	//아이디 중복확인 
	@RequestMapping(value = "/SduserRegistIdCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public String join(@RequestBody String sduemail) {
		
		logger.info("joinpart - error - sduser controller");
		
		SduserDto dto = sduserbiz.emailCheck(sduemail);
		if(dto == null) {
			return "true";
		}else if(dto != null) {
			return "false";
		}
		return "true";//?? 이코드 왜 필요..?
		
	}
	
	
	//회원가입 
	@RequestMapping(value = "/SduserRegist.do", method = RequestMethod.POST)
	public String nomalRegist(Model model, @ModelAttribute SduserDto dto) {
		
		SduserDto dto = sduserbiz.join(dto);
		
		return "RegistFormres";
	}

}
