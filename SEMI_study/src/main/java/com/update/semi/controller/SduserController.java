package com.update.semi.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SduserDto;
import com.update.semi.util.FileUtil;
import com.update.semi.util.UploadFileUtils;

import oracle.jdbc.proxy.annotation.Post;


@Controller
public class SduserController {
	
	@Resource(name = "uploadPath" )
	private String uploadPath ; 
	
	private Logger logger = LoggerFactory.getLogger(SduserController.class);
	
	@Autowired
	private SduserBiz sduserbiz;
	
	//메인페이지
	@RequestMapping(value="/main.do",method=RequestMethod.GET)
	public String main(HttpSession session) {
		session.getAttribute("sduserdto");
		logger.info("메인페이지에 도착하는 내용:"+session.getAttribute("sduserdto"));
		//값이  null !!!
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
			return "redirect:/boardlist.do";
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
			return "redirect:/boardlist.do"; //myPage로 안보내고 글목록ㅇ로 보낸다  
			
		}else {
			logger.info("sns회원가입이 완료 되었음 ");
			int res = sduserbiz.join(dto);
			if(res>0) {
				login = sduserbiz.login(dto);
				session.setAttribute("member", login);
				logger.info("sns회원가입 후 로그인 시켜준다. session생성하여 다음페이지로 넘김" +session.getAttribute("login"));
				return "redirect:/boardlist.do";
			}
			return "login";
		}
	}
	
	//마이페이지로 
	@RequestMapping(value = "/mypage.do", method = RequestMethod.GET)
	public String mypage(HttpSession session) {
		logger.info("여기는 마이페이지 컨트롤러 "+session.getAttribute("member")+"의 값을 보자 ");
		session.getAttribute("member");
		return "myPage";
	}
	
	//dto에 어노테이션으로 pattern을 지정하고, 빈값의 dto를 만들어서 updatepage에 전송해준다 
	@GetMapping("/updatemypage.do")
	public String updatemypage(HttpSession session, Model model) {
		//HttpSession session,
		
		logger.info("----get updatepage");
		//원래는 빈값을 보내줘야하는데, mypage를 유지시키고, 수정할 페이지만 하이버네이트를 쓰기위해서 session으루부터 값을 빼온다 
		SduserDto sduserdto = (SduserDto)session.getAttribute("member");
		model.addAttribute("member",sduserdto);
		System.out.println("세션의 값을 담아서 보내준다 "+sduserdto);
		return "updatemypage";
	}
	
	//오류가 생기면 담아서 보내준다 + 전체 수정사항 전송(부분 수정사항시 patch를 사용, 확실한 사용법 모름 ) 
	@PostMapping(value = "/updatemydata.do")
	public String updatemypageinsert(Model model, HttpSession session, @ModelAttribute("member") @Valid SduserDto sduserdto, BindingResult bindingresult) {
		logger.info("---post updatemypageinsert---");
		
		//에러를 담아서 보내줌 
		if(bindingresult.hasErrors()) {
			logger.info("유효성검사 >>>>>>>>>>>>>>>>>>>>> 실행");
	         List<ObjectError> list = bindingresult.getAllErrors();
	         for (ObjectError error : list) {
	            System.out.println(error);
	         }
			return "updatemypage";
		}
		
		
		//session에 담겨 있는 정보 담아주기 
		SduserDto sessiondto = (SduserDto)session.getAttribute("member");
		SduserDto dto = sduserbiz.selectOne(sessiondto.getSduemail());
		
		System.out.println("======================여기는 유저 컨트롤러 나와라 짠짠 ============"+dto);
		logger.info("흠 여기는 sddto"+sduserdto);
		
		if(dto.getSduimgpath() == null) {
			//1.저장해서 file의 경로를 만들어 주자 
			String UUIDfileName = UploadFileUtils.SP_ProfileUpload(sduserdto.getUserImgFile(),uploadPath);   // 파일 저장 실행 리턴값으로 파일명 반환 
		       // Dto에 경로 추가
		    sduserdto.setSduimgpath("/semi/resources/IMG/userimg/" + UUIDfileName);
		    
		    
				//수정을 해서 성공해을 시 마이페이로 넘어가기
				int res = sduserbiz.updatemypage(sduserdto);
				 
				logger.info("음 여기는 컨트롤러 결과값이 어케 나오나.. "+res);
				if(res != 0) {
				
					//수정된 정보를 세션에 새로 담는다  - 새로 담아올 정보에 대한 mapper가 필요하다 
					SduserDto updatelogin = sduserbiz.login(sduserdto);
					session.setAttribute("member", updatelogin);
					return "myPage";
				} 
	
		}else {
			
			
			// 파일저장 : 수정에서는 파일저장시  같은이름의 파일이 있다면 실행하지 말아야함
           //2-1. 이전 저장된 파일과 현재 올릴 파일의 이름을 비교한다 
		//String oldFileName =  sduserdto.getSduimgpath().replace("/semi/resources/IMG/userimg/P_", "");
			
			//oldFileName여기로 넘어노는 경로의 값은 null값이다, view에서 넘겨주는 값이 ""임으로 ㅠㅠ 업데이트 할 때마다 새롭게 사진을 올려주는 처리를 해준다 
			String oldFileName =  sduserdto.getSduimgpath();
			String[] fileNames = FileUtil.getFilesName(uploadPath);
			
			logger.info("+++++++++올드파일네임+++++++++++"+oldFileName);
			logger.info("+++++++++파일네임+++++++++++"+ Arrays.toString(fileNames));
			
            boolean chk = true;
            
            
            for(String name : fileNames) {
               System.out.println("컨트롤러에서 파일 확인 >>>>>>>>>>>>>>>>>>>>> " + name);
               //String splitName = name.split("_");
               if(name.equals(oldFileName)) {
                   chk = false;
               }
            }
            
            //수정시 사진파일 업로드 무조건 업로드 ㅠㅠㅠ 로 바꾸기 
            
            logger.info("프로필 이미지 업로드 실행 어떤 값이 출력이 되는가 >>>>>>>>>>>>>>>>>>>> "+chk );
            
            if(chk) { // 같은 이름의 파일이 있다면 true + not = false
               // 파일 데이터 받기
               MultipartFile file = sduserdto.getUserImgFile();
               // 파일 저장 실행 >> 리턴값으로 파일명 반환
               String UUIDfileName = UploadFileUtils.SP_ProfileUpload(file, uploadPath);    
               // Dto에 업로드된 경로 추가
               sduserdto.setSduimgpath("/semi/resources/IMG/userimg/" + UUIDfileName);
            }else if(chk == false){
            	sduserdto.setSduimgpath(sessiondto.getSduimgpath());
            	logger.info("기존에 값이 중복이 되었을 때 값을 출력 하라 "+sduserdto);
            }

            int updateRes = sduserbiz.updatemypage(sduserdto);
            if (updateRes > 0) {
               	
               // 추가한 정보로 세션 재할당
               SduserDto newLogin = sduserbiz.selectOne(dto.getSduemail());
               session.setAttribute("member", newLogin);
               return "redirect:/mypage.do";
            } else {
               logger.info("유저 마이페이지 update 실패");
               model.addAttribute("member",sduserdto);
               return "redirect:/updatemydata.do";
            }
            
		}
					
		return "redirect:/updatemydata.do";
	}
	
	/*
	// 프로필저장 : mentorDto insert, update
	   @RequestMapping(value = "/profileInsert.do", method = RequestMethod.POST)
	   public String mentorProfileInsert(Model model, HttpSession session, @ModelAttribute("mentorDto") @Valid MentorDto mentorDto,
	         BindingResult result) throws IOException {
	    

	      if (result.hasErrors()) {
	         List<ObjectError> list = result.getAllErrors();
	         for (ObjectError error : list) {
	         }
	         model.addAttribute("mentorDto", mentorDto);
	         return "mentor/MENTOR_mentorProfile";
	      } else {
	         // MEMBER_JOIN 테이블 MEMBER_PROFILE 테이블 join >>> MentorDto
	         // id을 사용하여 profile 테이블에 추가할 멘토 정보가 있는지 확인
	         MentorDto dto = profileBiz.MentorSelectOne(mentorDto.getId());

	         // profile테이블에 없으면 insert >>> 세션 변경
	         if (dto == null) {
	            // 최초프로필 작성시  joinDto에서 정보를 추가로 가져온다 
	            JoinDto login = (JoinDto) session.getAttribute("login");
	         
	         // 파일저장 : 최초 insert시에는 무조건 파일저장 실행   
	            // 파일 데이터와 경로를 인지로 전달 >>> 파일 저장
	            String UUIDfileName = UploadFileUtils.SP_ProfileUpload(mentorDto.getMemberFile(), uploadPath);   // 파일 저장 실행 리턴값으로 파일명 반환 
	            // Dto에 경로 추가
	            mentorDto.setMemberContent("/update/resources/img/mentor/" + UUIDfileName);

	            // joinDto + 추가 프로필 정보 = mentorDto
	            mentorDto.setDto(login);
	            int isertRes = profileBiz.MentorProfileInsert(mentorDto);
	            if (isertRes > 0) {
	               logger.info("멘토 profile insert 성공");
	               //프로필 작성여부 칼럼 JoinRegisterYn >> Y로 변경
	               int updateJoinRegister = joinBiz.updateJoinRegister(mentorDto.getId(), "Y");
	               if(updateJoinRegister > 0) {
	                  logger.info("멘토 profile 프로필 작성여부 Y로 변경 성공");
	                  // joinDto + 추가 프로필 정보를 불러와서 세션 재할당
	                  MentorDto newLogin = profileBiz.MentorSelectOne(mentorDto.getId());
	                  session.setAttribute("login", newLogin);
	                  return "redirect:/mentor/main.do";
	               } else {
	                  logger.info("멘토 profile 프로필 작성여부 Y로 변경 실패!!!!");
	                  model.addAttribute("mentorDto",mentorDto);
	                  return "mentor/MENTOR_mentorProfile";
	               }
	            } else {
	               return "mentor/MENTOR_mentorProfile";
	            }
	            // profile테이블에 있으면 update >>> 세션 변경
	         } else {
	            
	         // 파일저장 : 수정에서는 파일저장시  같은이름의 파일이 있다면 실행하지 말아야함
	            String oldFileName =  mentorDto.getMemberContent().replace("/update/resources/img/mentor/P_", "");
	            String[] fileNames = Util.getFilesName(uploadPath);
	            
	            boolean chk = true;
	            for(String name : fileNames) {
	               System.out.println("파일 확인 >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + name);
	               if(name.equals(oldFileName)) {
	                  chk = false;
	               }
	            }
	            
	            //if(chk) { // 같은 이름의 파일이 있다면 true + not = false
	               logger.info("프로필 이미지 업로드 실행 >>>>>>>>>>>>>>>>>>>> " +  mentorDto.getMemberFile().getOriginalFilename());
	               
	               // 파일 데이터 받기
	               MultipartFile file = mentorDto.getMemberFile();
	               // 파일 저장 실행 >> 리턴값으로 파일명 반환
	               String UUIDfileName = UploadFileUtils.SP_ProfileUpload(file, uploadPath);    
	               // Dto에 업로드된 경로 추가
	               mentorDto.setMemberContent("/update/resources/img/mentor/" + UUIDfileName);
	            //}

	            int updateRes = profileBiz.MentorProfileUpdate(mentorDto);
	            if (updateRes > 0) {
	               logger.info("멘토 profile update 성공");
	               // 추가한 정보로 세션 재할당
	               MentorDto newLogin = profileBiz.MentorSelectOne(mentorDto.getId());
	               session.setAttribute("login", newLogin);
	               return "redirect:/mentor/main.do";
	            } else {
	               logger.info("멘토 profile update 실패");
	               model.addAttribute("mentorDto",mentorDto);
	               return "mentor/MENTOR_mentorProfile";
	            }
	         }
	      }
	   }
	*/

}
