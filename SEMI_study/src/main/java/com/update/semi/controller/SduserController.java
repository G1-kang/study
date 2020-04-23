package com.update.semi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SduserDto;


@Controller
public class SduserController {
	
	private Logger logger = LoggerFactory.getLogger(SduserController.class);
	
	@Autowired
	private SduserBiz sduserbiz;
	
	//메인페이지
	@RequestMapping(value="/main.do",method=RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	//회원가입 페이지로 
	@RequestMapping(value="/joinform.do", method = RequestMethod.GET)
	public String joinPage(Model model) {
		return "RegistForm";
	}
	//회원가입 
	@RequestMapping(value = "/SduserRegist.do", method = RequestMethod.POST)
	public String join(@ModelAttribute SduserDto dto) {
		
		System.out.println("컨트롤러 회원가입 부분 : "+dto);
		
		
		int res = sduserbiz.join(dto);
		
		if(res > 0) {
			return "login";	// 로그인으로 가
		}
		return "RegistForm"; 
		
	}
	
	//아이디 중복확인 
	@RequestMapping(value = "/SduserRegistIdCheck.do", method = RequestMethod.POST,
			produces ="application/text; charset=utf8")
	@ResponseBody
	public String emailCheck(@ModelAttribute("sduemail") String sduemail) {
		//@ResponseBody를 사용할 때 짝꿍 
		//@ModelAttribute 와 @RequestBody 두개 requestbody쪽이 쓰기 힘들다 
		//requestbody는 바인딩을 해주는데 dto안에 값을 넣어줌으로 회원가입시 한번에 많은 양을 넣을 때 편리하게 사용 가능하다 ,dto...!안에다가 값을 넣어주는 것 
		
		logger.info("joinpart - error - sduser controller");
		System.out.println("여기는 아이디 중복확인 컨트롤러 !"+sduemail);
		
		SduserDto dto = sduserbiz.emailCheck(sduemail);
		System.out.println("중복확인에서 받아온 id값은?"+dto);
		
		if(dto.getSduemail() == null) {//여기 안들어옴 
			System.out.println("흠.... 여기는 컨트롤러 중복확인 파트 : "+dto.getSduemail());
			return "true";
		}	
		  return "false";
	}
	
	

	
	// 로그인 페이지로
	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	// 로그인 성공 >>> select
	// 로그인 성공 세션 >>> 스코프  : 페이지 > 리퀘스트 > 세션 > 애플리케이션
	// 로그인 성공 페이지
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(SduserDto dto, HttpServletRequest req) {
		logger.info("post login- controller");
		
		HttpSession session = req.getSession();
		SduserDto logindto = sduserbiz.login(dto);
		if(logindto == null) {
			session.setAttribute("member", null);
		}else {
			session.setAttribute("member", logindto);
		}
		
		return "myPage";
	}
	
	//로그아웃 
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("get logout - controller");
		session.invalidate();
		
		return "redirect:/main.do";
	}

}
