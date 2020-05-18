package com.update.semi.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonObject;
import com.update.semi.biz.SdboardBiz;
import com.update.semi.biz.SduserBiz;
import com.update.semi.dto.SdboardDto;
import com.update.semi.dto.SduserDto;

@Controller
public class SdboardController {

	private Logger logger = LoggerFactory.getLogger(SdboardController.class);
	
	@Autowired
	private SduserBiz sduserbiz;
	@Autowired
	private SdboardBiz sdboardbiz; 
	
	//글 목록 페이지로 
	@RequestMapping(value = "/boardlist.do", method = RequestMethod.GET)
	public String boardlist(Model model, HttpSession session){
		
		SduserDto login = (SduserDto) session.getAttribute("member");
		System.out.println("여긴 게시판 로그인 세션을 가져옴 "+login);
		
		List<SdboardDto> boardlist = sdboardbiz.list();
		System.out.println("여긴 게시판 컨트롤러 "+boardlist);
		
		model.addAttribute("boardlist", boardlist);
		
		//session.getAttribute("sduserdto");
		//logger.info("보드페이지로 갔을때 전달되는 값:"+session.getAttribute("sduserdto"));
		//값이 null  !!! 
		
		return "boardlist";
	}
	
	//글 상세보기 
	@RequestMapping(value ="/board.do", method = RequestMethod.GET)
	public String board(int sdbseq, Model model) {
		
		SdboardDto sdboarddto = sdboardbiz.selectOne(sdbseq);
		model.addAttribute("boardone", sdboarddto);
		logger.info("글 상세 보기 컨트롤러 : "+sdboarddto);
		return "board";
	}
	
	//글쓰기 페이지로 
	@RequestMapping(value ="/boardwrite.do", method = RequestMethod.GET)
	public String boardwrite(HttpSession session) {
		
		session.getAttribute("member");
		return "boardwrite";
	}
	//글쓰기 후 데이터 전송 
	@RequestMapping(value ="/boardwriteres.do", method = RequestMethod.POST)
	public String boardwriteres(SdboardDto sdboarddto, HttpServletResponse response) {
		
		logger.info("글쓰기 값 :"+sdboarddto);
		//1이상일때 alert띄워주기 
		int res = sdboardbiz.write(sdboarddto);
		if(res>0) {
			try {
				jsPrint("글 작성 하였습니다. ", "boardwriteres.do", response);
			} catch (IOException e) {
				logger.info("글쓰기 에서 에러남 ");
				e.printStackTrace();
			}
		}
		
		return "redirect:/boardlist.do";
	}
	

	
	
	
	
	//글 수정 
	@RequestMapping(value ="/boardupdate.do", method = RequestMethod.GET)
	public String boardupdate(Model model,int sdbseq) {
		
		SdboardDto updatedto = sdboardbiz.selectOne(sdbseq);
		model.addAttribute("updatedto", updatedto);
		
		return "boradupdate";
	}
	
	//글 수정 res
	@RequestMapping(value ="/boardupdateres.do", method = RequestMethod.POST)
	public String boardupdateres(SdboardDto sdboarddto, Model model) {
		
		int res = sdboardbiz.update(sdboarddto);
		
		if(res>0) {
			
			SdboardDto updateres = sdboardbiz.selectOne(sdboarddto.getSdbseq());
			model.addAttribute("updateres", updateres);
			
			return "redirect:/board.do";
		}
		
		return "boardupdate";
	}
	
	//글 삭제 
	@RequestMapping(value ="/boarddelete.do", method = RequestMethod.GET)
	public ModelAndView boarddelete(HttpSession session,int sdbseq) {
		
		ModelAndView mav = new ModelAndView(); 
		
		int res = 0; 
		res = sdboardbiz.delete(sdbseq);
		//1일때 alert 띄워주기 
		if(res>0) {
			String mag = "글이 삭제 되었습니다.";
			//!!!적용이 안됨 ~ msg글 안넘어감 !
			mav.setView(new RedirectView("boardlist.do")); //새로운 list페이지로 이동시킴 
			mav.addObject("msg", mag);
			session.getAttribute("member");
			logger.info("메시지와 가야할 페이지 :"+mav);
			logger.info("여기까지 왔나 ? ----------------"+session.getAttribute("member"));
			//model.addAttribute("msg", "글이 삭제 되었습니다.");
//			jsPrint("글이 삭제 되었습니다.", "boardlist.do", response);
		}
		return mav ;
	}
	
	//alert띄우기 
	public void jsPrint(String msg, String url, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html; charset=UTF-8"); // 해당 코드가 없으면 글자가 깨진다 >>> web.xml에 filter를 했음에도 깨지는 이유는를 모르겠음
	      String s = "<script type='text/javascript'>" + " alert('" + msg + "'); " + "location.href ='" + url + "';"
	            + "</script>";
	      PrintWriter out = response.getWriter();
	      out.print(s);
	   }
	
	
	
	
	
	
	
}
