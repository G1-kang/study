package com.update.semi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SduserDto;

import oracle.jdbc.proxy.annotation.Post;


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
	public String login(SduserDto dto, HttpServletRequest req, HttpServletResponse response) throws IOException {
		logger.info("post login- controller");
		
		HttpSession session = req.getSession();
		SduserDto logindto = sduserbiz.login(dto);
		if(logindto == null) {
			response.setContentType("text/html; charset=UTF-8");
			 
			PrintWriter out = response.getWriter();
			 
			out.println("<script>alert('회원가입을 먼저 해주세요 '); </script>");
			 
			out.flush();


			return "RegistForm";
		}else {
			session.setAttribute("member", logindto);
			return "myPage";
		}
		
	}
	
	//로그아웃 
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("get logout - controller");
		session.invalidate();
		
		return "main";
	}
	
	//sns로그인  !!!
	@RequestMapping(value = "/snslogin.do", method = RequestMethod.POST)
	public String snslogin(HttpSession session, SduserDto dto) throws Exception {
		logger.info("sns로그인");
		
		SduserDto login = null;
		//아이디 있는 경우 -> 로그인 , 아이디 없는 경우 -> 회원가입 -> 로그인화면으로
		SduserDto snsemailchk = sduserbiz.snsemailchk(dto.getSduemail());
		logger.info("=====snsemailchk:"+snsemailchk);
		
		//아이디가 있을 떄, 로그인 
		if(snsemailchk.getSduemail() != null) {
			logger.info("sns로그인 하기");
			login = sduserbiz.login(dto);
			logger.info("ㄴsns로그인 정보 :"+dto+"아이디 검색:"+snsemailchk.getSduemail()+"로그인됨:"+login);
			session.setAttribute("member", login);//session은 어느 페이지 까지 존재할지 알려주는애 
			return "myPage"; //myPage로 가는애 
			
		}else {
			logger.info("sns회원가입이 완료 되었음 ");
			int res = sduserbiz.join(dto);
			if(res>0) {
				login = sduserbiz.login(dto);
				session.setAttribute("member", login);
				logger.info("sns회원가입 후 로그인 시켜준다. session생성하여 다음페이지로 넘김" +session.getAttribute("login"));
				return "myPage";
			}
			return "login";
		}
	}
	
	//dto에 어노테이션으로 pattern을 지정하고, 빈값의 dto를 만들어서 updatepage에 전송해준다 
	@GetMapping("/updatemypage.do")
	public String updatemypage(HttpSession session, Model model) {
		//HttpSession session,
		
		logger.info("----get updatepage");
		//원래는 빈값을 보내줘야하는데, mypage를 유지시키고, 수정할 페이지만 하이버네이트를 쓰기위해서 session으루부터 값을 빼온다 
		SduserDto sduserdto = (SduserDto)session.getAttribute("member");

		model.addAttribute("sduserdto",sduserdto);
		System.out.println("세션의 값을 담아서 보내준다 "+sduserdto);
		return "updatemypage";
	}
	
	//오류가 생기면 담아서 보내준다 + 전체 수정사항 전송(부분 수정사항시 patch를 사용, 확실한 사용법 모름 ) 
	@PostMapping(value = "/updatemydata1.do")
	public String updatemypageinsert(HttpSession session, @ModelAttribute("sduserdto") @Valid SduserDto sduserdto, BindingResult bindingresult) {
		logger.info("---post updatemypageinsert---");
		System.out.println("여기는 컨트롤러 풋맵핑"+sduserdto);
		
		//에러를 담아서 보내줌 
		if(bindingresult.hasErrors()) {
			logger.info("유효성검사 >>>>>>>>>>>>>>>>>>>>> 실행");
	         List<ObjectError> list = bindingresult.getAllErrors();
	         for (ObjectError error : list) {
	            System.out.println(error);
	         }
			return "updatemypage";
		}
		
		
		System.out.println("여기는 마이페이지에서 수정 버튼을 누른후의 값 "+sduserdto);
		//수정을 해서 성공해을 시 마이페이로 넘어가기 
		int res = sduserbiz.updatemypage(sduserdto); 
		logger.info("음 여기는 컨트롤러 결과값이 어케 나오나.. "+res);
		if(res != 0) {
		
			//수정된 정보를 세션에 새로 담는다  - 새로 담아올 정보에 대한 mapper가 필요하다 
			SduserDto updatelogin = sduserbiz.login(sduserdto);
			session.setAttribute("sduserdto", updatelogin);
			return "myPage";
		} 
		return "redirect:/updatemydata.do";
		
		
		/*
		else {
			//수정실패 경우 현재 페이지로
			return "redirect:/updatemydata.do"; 	
		}
		*/
		
	}
	

}
