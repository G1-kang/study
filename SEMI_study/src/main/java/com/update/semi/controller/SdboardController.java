package com.update.semi.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.update.semi.dto.OraclePagination;
import com.update.semi.dto.SdboardDto;
import com.update.semi.dto.SduserDto;
import com.update.semi.util.DownloadFileUtils;
import com.update.semi.util.UploadFileUtils;
import com.update.semi.util.Util;

@Controller
//RequestMapping("/board")

public class SdboardController {

	private Logger logger = LoggerFactory.getLogger(SdboardController.class);

	@Autowired
	private SduserBiz sduserbiz;
	@Autowired
	private SdboardBiz sdboardbiz; // xml설정을 해주는 것 fileupload.xml에 bean 설정

	@Resource(name = "boardImgPath")
	private String imgUploadPath;
	@Resource(name = "boardFilePath")
	private String fileUploadPath;
	
	// 글 목록 페이지로
	@RequestMapping(value = "/boardlist.do", method = RequestMethod.GET)
	public String boardlist(Model model, HttpSession session, @ModelAttribute SdboardDto sdboarddto, @RequestParam(defaultValue = "1") int currentPage) {

		logger.info("board list page >>>>>>[input]sdboarddto"+sdboarddto);
		SduserDto login = (SduserDto) session.getAttribute("member");
		System.out.println("여긴 게시판 로그인 세션을 가져옴 " + login);
		
		//1)전체 게시물 개수 가져오기 
	    int totalBoardCount = sdboardbiz.getTotalBoard(sdboarddto);   // 전체게시물 수  or 검색한 게시물 수
	    logger.info("board main 총 게시판 합 : " + totalBoardCount);  
	    
	      /*2) 페이징 클래스 >> 쿼리에 필요한 시작페이지 번호, 끝 페이지 번호를 계산해서 가지고 있음  */
	    OraclePagination pagination = new OraclePagination(totalBoardCount, currentPage);   // 전체 게시물 수, 현재 페이지 (== 요청된 페이지) 
	    logger.info("board main page >>>>>>>>>>>>>>> [페이징] OraclePagination : " + pagination );
	      
	      
	      //3) boardDto에 시작 페이지, 끝 페이지 추가
	    sdboarddto.setStartCount(pagination.getStartBoardNo());
	    sdboarddto.setEndCount(pagination.getEndBoardNo());
		
	  
		
		List<SdboardDto> boardlist = sdboardbiz.boardList(sdboarddto);
		logger.info("게시판 list "+boardlist);
		logger.info("게시판 list 담겨들어가는 값 sdboarddto:"+sdboarddto);

		//페이징 처리가 된 값들 리턴해야함 
		model.addAttribute("boardlist", boardlist);
	    model.addAttribute("pagination", pagination);
	    model.addAttribute("login",login);
		return "boardlist";
	}
	

	

	/*
	// 글 상세보기
	@RequestMapping(value = "/board.do", method = RequestMethod.GET)
	public String board(int sdbseq, Model model) {

		SdboardDto sdboarddto = sdboardbiz.selectOne(sdbseq);
		model.addAttribute("boardone", sdboarddto);
		logger.info("글 상세 보기 컨트롤러 : " + sdboarddto);
		return "board";
	}
	*/
	//************************
	   // 디테일
	   @RequestMapping(value="/board.do", method = RequestMethod.GET)
	   public String boardDetail(Model model, @RequestParam("sdbseq") int sdbseq, SdboardDto sdboarddto) {
	      logger.info("board detail page");

	      sdboarddto = sdboardbiz.selectOne(sdbseq);
	      if(sdboarddto.getSdbfilpath() != null) {
	         if(sdboarddto.getSdbfilpath().contains("??")) {
	            String[] fileNames = sdboarddto.getSdbfilpath().split("\\?\\?");  // "\\" 두개를 붙이는 이유는  Meta character라서 정규식을 기반으로 구현한 메서드에 그대로 사용 불가하다.
	            /* 
	                # Meta character: / ? *
	                 정규 표현식에는 특별한 의미를 없애고 문자 그대로 표현식 내에서 처리하기 위해 이스케이프해야하는 14 개의 메타 문자
	             */
	            sdboarddto.setSdbfilpath("첨부된 파일 " + fileNames.length +"개 ");
	         } else {
	            //int index = sdboarddto.getSdbfilpath().lastIndexOf("_") + 1;
	            sdboarddto.setSdbfilpath("첨부된 파일 1개 ");
	         }
	      }
	      model.addAttribute("boardone", sdboarddto);
	      
	      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : " + sdboarddto);
	      return "board";
	   }
	   
	   
	   @RequestMapping(value="/fileDetail.do", method = RequestMethod.POST)
	   @ResponseBody
	   public Map<String, Object> fileDown(@RequestParam("sdbseq") int sdbseq,  SdboardDto sdboarddto) {
	      Map<String, Object> output = new HashMap<String, Object>();
	      
	      sdboarddto = sdboardbiz.selectOne(sdbseq);
	      if(sdboarddto.getSdbfilpath() != null) {
	         String fileFullNames = sdboarddto.getSdbfilpath(); 
	         if(fileFullNames.contains("??")) {
	            String[] fileFullNameArr = fileFullNames.split("\\?\\?");  // "\\" 두개를 붙이는 이유는  Meta character라서 정규식을 기반으로 구현한 메서드에 그대로 사용 불가하다.
	            String[] fileNameArr = new String[fileFullNameArr.length];
	            for(int i = 0; i < fileFullNameArr.length; i++) {
	               int index = fileFullNameArr[i].lastIndexOf("_") + 1;
	               fileNameArr[i] = fileFullNameArr[i].substring(index, fileFullNameArr[i].length());
	            }
	            output.put("msg", "success");
	            output.put("fileName", fileNameArr);
	            
	         } else {
	            // 파일이 1개 일때
	            int index = sdboarddto.getSdbfilpath().lastIndexOf("_") + 1;
	            String fileName = fileFullNames.substring(index, fileFullNames.length());
	            output.put("msg", "success");
	            output.put("fileName", fileName);
	         }
	      } else {
	         output.put("msg", "fail");
	      }
	      
	      return output;
	   }
	   
	   // 파일 다운로드
	   @RequestMapping(value="/fileDown.do", method = RequestMethod.POST)
	   @ResponseBody
	   public byte[] fileDown(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileName") String fileName ,@RequestParam("sdbseq") int sdbseq, SdboardDto sdboarddto) throws UnsupportedEncodingException {
	      logger.info("board file down");
	      byte[] down = null;
	      String outFilePath = "";
	      
	      sdboarddto = sdboardbiz.selectOne(sdbseq);
	      if(sdboarddto.getSdbfilpath() != null) {
	         String fileFullNames = sdboarddto.getSdbfilpath(); 
	         if(fileFullNames.contains("??")) {
	            String[] fileFullNameArr = fileFullNames.split("\\?\\?");  // "\\" 두개를 붙이는 이유는  Meta character라서 정규식을 기반으로 구현한 메서드에 그대로 사용 불가하다.
	            for(int i = 0; i < fileFullNameArr.length; i++) {      
	               int index = fileFullNameArr[i].lastIndexOf("_") + 1;   // 뒤에서 처음으로 _가 나오는 인덱스 번호를 찾는다.
	               String tempFileName = fileFullNameArr[i].substring(index, fileFullNameArr[i].length());   // 원본 파일명을 가져온다.
	               if(fileName.equals(tempFileName)) {         // 다운요청한 파일명과 일치하는 파일명을 찾는다
	                  outFilePath = fileFullNameArr[i];
	               }
	            }
	         } else {
	            int index = fileFullNames.lastIndexOf("_") + 1;   // 뒤에서
	            String tempFileName = fileFullNames.substring(index, fileFullNames.length());
	            if(fileName.equals(tempFileName)) {         // 다운요청한 파일명과 일치하는 파일명을 찾는다
	               outFilePath = fileFullNames;
	            }
	         }
	         // 단일 파일 다운로드
	         logger.info("[fileDown.do] >>>>>>>>>>>> 다운로드 파일명 : " + outFilePath);
	         File file = new File(outFilePath);
	         down = DownloadFileUtils.file_toByte(file);   // == FileCopyUtils.copyToByteArray(file);   #스프링에서 제공하는 파일 다운로드 유틸 
	            
	         String filename = new String(file.getName().getBytes("utf-8"), "8859_1");             // 파일 이름을 "utf-8"의 바이트 코드로 변환, 8859_1 인코딩 설정
	         response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");   
	         response.setContentType("application/octet-stream");                            // # application/octet-stream는 다른 모든 경우를 위한 기본값입니다. ,알려지지 않은 파일 타입은 이 타입을 사용해야 합니다
	         response.setContentLength(down.length);
	         
	         //HttpHeaders headers = new HttpHeaders();  >>> 스프링에서 지원하는 Http 헤더설정 클래스 
	      }
	      return down;
	   }
	//***************************
	

	// 글쓰기 페이지로
	@RequestMapping(value = "/boardwrite.do", method = RequestMethod.GET)
	public String boardwrite(HttpSession session) {

		session.getAttribute("member");
		return "boardwrite";
	}

	// !!! 글쓰기 버튼을 사용하지 않고 있따.
	// 글쓰기 후 데이터 전송
	/*
	 * @RequestMapping(value ="/boardwriteres.do", method = RequestMethod.POST)
	 * public String boardwriteres(SdboardDto sdboarddto, HttpServletResponse
	 * response) {
	 * 
	 * logger.info("글쓰기 값 :"+sdboarddto); //1이상일때 alert띄워주기 int res =
	 * sdboardbiz.write(sdboarddto); if(res>0) { try { jsPrint("글 작성 하였습니다. ",
	 * "boardwriteres.do", response); } catch (IOException e) {
	 * logger.info("글쓰기 에서 에러남 "); e.printStackTrace(); } }
	 * 
	 * return "redirect:/boardlist.do"; }
	 */

	// 글 수정
	/*
	@RequestMapping(value = "/boardupdate.do", method = RequestMethod.GET)
	public String boardupdate(Model model, int sdbseq) {

		SdboardDto updatedto = sdboardbiz.selectOne(sdbseq);
		model.addAttribute("updatedto", updatedto);

		return "boradupdate";
	}
	*/
	/*
	// 글 수정 res
	@RequestMapping(value = "/boardupdateres.do", method = RequestMethod.POST)
	public String boardupdateres(SdboardDto sdboarddto, Model model) {

		int res = sdboardbiz.update(sdboarddto);

		if (res > 0) {

			SdboardDto updateres = sdboardbiz.selectOne(sdboarddto.getSdbseq());
			model.addAttribute("updateres", updateres);

			return "redirect:/board.do";
		}

		return "boardupdate";
	}
*/
	// 글 삭제
	@RequestMapping(value = "/boarddelete.do", method = RequestMethod.GET)
	public ModelAndView boarddelete(HttpSession session, int sdbseq) {

		ModelAndView mav = new ModelAndView();

		int res = 0;
		res = sdboardbiz.delete(sdbseq);
		// 1일때 alert 띄워주기
		if (res > 0) {
			String mag = "글이 삭제 되었습니다.";
			// !!!적용이 안됨 ~ msg글 안넘어감 !
			mav.setView(new RedirectView("boardlist.do")); // 새로운 list페이지로 이동시킴
			mav.addObject("msg", mag);
			session.getAttribute("member");
			logger.info("메시지와 가야할 페이지 :" + mav);
			logger.info("여기까지 왔나 ? ----------------" + session.getAttribute("member"));
			// model.addAttribute("msg", "글이 삭제 되었습니다.");
//			jsPrint("글이 삭제 되었습니다.", "boardlist.do", response);
		}
		return mav;
	}

	// alert띄우기
	public void jsPrint(String msg, String url, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8"); // 해당 코드가 없으면 글자가 깨진다 >>> web.xml에 filter를 했음에도 깨지는 이유는를
																// 모르겠음
		String s = "<script type='text/javascript'>" + " alert('" + msg + "'); " + "location.href ='" + url + "';"
				+ "</script>";
		PrintWriter out = response.getWriter();
		out.print(s);
	}

	// 비동기 멀티 이미지 업로드
	@ResponseBody
	@RequestMapping(value = "/AjaxFileUplod.do")
	public Map<String, Object> AjaxFileUplod(@ModelAttribute("fileArr") MultipartFile[] fileArr, SdboardDto sdboardDto,
			HttpSession session) throws IOException {
		logger.info("[ajax] Ajax File Uplod : >>>>>>>>>>>>>>>>>>>>>  " + fileArr);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + fileArr.length);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("msg", "fail"); // 디폴트 fail
		output.put("sdbseq", "0"); // 디폴트 0 >>> 게시판 insert 성공시 seq를 담는다

		// 회원의 email 주소 = id
		String id = "";
		String sdunick ="";
		// #1 유저id, 년, 월, 일 폴더 생성
		String id_ymdPath = "";
		SduserDto sduserDto = (SduserDto) session.getAttribute("member");
		if (sduserDto != null) {
			logger.info("폴더생성");
			sdunick = sduserDto.getSdunick();
			id = sduserDto.getSduemail();
			// id_ymdPath 파일 경로 설정 >>> /유져id/년/월/일
			id_ymdPath = UploadFileUtils.calcPath(imgUploadPath, id);
		}

		// #2 파일 저장후 성공시 "view에서 불러올 경로 + 파일명" 배열에 담는다[썸내일 제외 원본이미지], 썸내일은 따로 "view에서
		// 불러올 경로 + 파일명"을 String에 담는다. >> 향후 실패 처리시를 만들어야함
		String[] imgNameArr = new String[fileArr.length];
		String thumbImgName = "";

		int j = 0;
		for (MultipartFile file : fileArr) {
			if (file.getSize() != 0) {// file은 항상 잇기 때문에 size가 0일때리를 봐야한다
				if (j == 0) {
					// 파일과 썸내일 생성 >> 썸내일이름[0], 원본파일이름[1] 배열로 리턴
					String[] tempName = UploadFileUtils.imgUploadAndThumb(imgUploadPath, file.getOriginalFilename(),
							file.getBytes(), id_ymdPath);
					// DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + /s/ + / 썸내일 파일명
					thumbImgName = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + "/s/" + tempName[0];
					// DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + / + 파일명
					imgNameArr[0] = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + File.separator
							+ tempName[1];

				} else {
					// 파일 생성
					String fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(),
							file.getBytes(), id_ymdPath);
					// DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + / + 파일명
					imgNameArr[j] = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + File.separator
							+ fileName;
				}
				j++;
			}
		}

		// #3 DB저장 (id, 썸네일이미지명, 이미지명) 저장
		sdboardDto.setSduemail(id);
		sdboardDto.setThumbnail(thumbImgName);
		sdboardDto.setSdunick(sdunick);
		
		String imgNames = "";
		for (int i = 0; i < imgNameArr.length; i++) {
			if (i == 0) {
				imgNames = imgNameArr[0];
			} else {
				imgNames = imgNames + "??" + imgNameArr[i];
			}
		}
		sdboardDto.setSdbimgpath(imgNames);

		int res = sdboardbiz.insertImg(sdboardDto);
		System.out.println("4.inserImg의 값이 들어 이쓴ㄴ지 보자"+sdboardDto);
		
		if (res > 0) {
			logger.info("Board img 추가 성공");
			System.out.println("5.여기까지 dto는 들어오나?"+sdboardDto);
			// #4 방금 추가한 boardNo 알아낸다.(id, 썸내일 이름를 통해 seq를 알아낸다.)
			String boardNo = sdboardbiz.getBoardNo(sdboardDto); // map에 담아서 보내줘야 하기 때문에 string으로 바꿔 담아 준다
			
			logger.info("Board >>>>>>>>>>>>>>>  추가한 BoardNo : " + boardNo);
			System.out.println("1.여기는 추가한 값이 들어와 있는지 ?"+boardNo);
			// #5 여기까지 성공 헀다면 output를 만들어 보낸다
			if (boardNo != null ) {
				output.put("imgNameArr", imgNameArr);
				output.put("msg", "success");
				output.put("sdbseq", boardNo);
			}
		}
		return output;
	}

	// 글쓰기 완료
	@RequestMapping(value = "/boardwriteres.do", method = RequestMethod.POST)
	public String boardWriteRes(Model model, @ModelAttribute SdboardDto sdboarddto, HttpSession session)
			throws IOException {
		logger.info("board Write Res >>>>>>>>>>>>>>>>>>>>> " + sdboarddto);

		SduserDto sduserdto = (SduserDto) session.getAttribute("member");
		String id = "";
		if (sduserdto != null) {
			id = sduserdto.getSduemail();
		}

		MultipartFile[] fileArr = sdboarddto.getFile();
		String fileNames = "";

		logger.info("fileArr[0]에 뭐가 담겨있나요 ? "+ fileArr[0].getSize()+"흠?"+fileArr[0].getOriginalFilename()); // 얘 타입에 맞춰서 if 다시 돌려보자.
		
		// 파일 업로드
		if(fileArr[0].getSize() != 0 ) {
			System.out.println("1.파일이 담겨 있음을 확인 ");
			// 게시판 첨부파일 업로드~
			// 폴더 생성 >> fileUploadPath + /id/yyyy/mm/dd/
			String id_ymdPath = UploadFileUtils.calcPath(fileUploadPath, id);
			System.out.println("2.파일 이후에 id에 해당하는 주소생성"+id_ymdPath);
			for (int i = 0; i < fileArr.length; i++) {
				System.out.println("필요없는 이름을 찾아서 : "+fileArr[i].getOriginalFilename());
				if (i == 0) {
					String temp = UploadFileUtils.fileUpload(fileUploadPath, fileArr[i].getOriginalFilename(), fileArr[i].getBytes(), id_ymdPath);
					fileNames = fileUploadPath + id_ymdPath + File.separator + temp;
				} else {
					String temp = UploadFileUtils.fileUpload(fileUploadPath, fileArr[i].getOriginalFilename(), fileArr[i].getBytes(), id_ymdPath);
					fileNames = fileNames + "??" + fileUploadPath + id_ymdPath + File.separator + temp;
				}
			}
		}

		// id, fileNames(업로드한 파일명) dto 추가
		sdboarddto.setSduemail(id);
		sdboarddto.setSdbfilpath(fileNames);	  // 첨부된 파일이 없다면 "" DB에 들어감
		System.out.println("3.sdboarddto에 새로 담은 값이 들어 갔는지 보자 "+sdboarddto);
		// DB 추가 = 업로드한 이미지 x , 업로드한 파일 o
		if (sdboarddto.getSdbseq() == 0) {
			logger.info("board Write Res >>>>>>>>>>>>>>>> [기존 이미지 없음] Board insert " + sdboarddto);

			int insertRes = sdboardbiz.insertNoImgBoard(sdboarddto);

			if (insertRes > 0) {
				logger.info("board Write Res >>>>>>>>>>>>>>>> [추가 성공] Board insert success");
				return "redirect:/boardlist.do";
			} else {
				logger.info("board Write Res >>>>>>>>>>>>>>>> [추가 실패] Board insert fail");
				// date 포멧 변환
				// title, content 길이 변경
				model.addAttribute("sdboarddto", sdboarddto);
				return "redirect:/boardwrite.do";
			}
			// DB 수정 = 업로드한 이미지 o , 업로드한 파일 o
		} else {
			logger.info("board Write Res >>>>>>>>>>>>>>>> [기존 이미지 있음] Board update" + sdboarddto);

			int updateRes = sdboardbiz.updateRestContent(sdboarddto);

			if (updateRes > 0) {
				logger.info("board Write Res >>>>>>>>>>>>>>>> [수정 성공] Board update success");
				return "redirect:/boardlist.do";
			} else {
				logger.info("board Write Res >>>>>>>>>>>>>>>> [수정 실패] Board update fail");
				// date 포멧 변환
				// title, content 길이 변경
				model.addAttribute("sdboarddto", sdboarddto);
				return "redirect:/boardwrite.do";
			}

		}
	}
	
	/*
	// 글 수정 res
	@RequestMapping(value = "/boardupdateres.do", method = RequestMethod.POST)
	public String boardupdateres(SdboardDto sdboarddto, Model model) {

		int res = sdboardbiz.update(sdboarddto);

		if (res > 0) {

			SdboardDto updateres = sdboardbiz.selectOne(sdboarddto.getSdbseq());
			model.addAttribute("updateres", updateres);

			return "redirect:/board.do";
		}

		return "boardupdate";
	}
	*/
	
/////////////////////////////////////////////////////////
	
	// 수정하기 페이지
	   @RequestMapping(value="/boardupdate.do", method = RequestMethod.GET)
	   public String boardupdate(Model model, @RequestParam("sdbseq") int sdbseq) {
	      logger.info("board update page go boardNO : " + sdbseq);
	       
	      SdboardDto updateDto = sdboardbiz.selectOne(sdbseq);
	      model.addAttribute("updatedto", updateDto);
	      
	      return "boradupdate";
	   }
	   
	   // 수정하기 이미지 업로드
	   @RequestMapping(value="/AjaxFileUpdate.do", method = RequestMethod.POST)
	   @ResponseBody
	   public Map<String, Object> AjaxFileUpdate(@ModelAttribute("fileArr") MultipartFile[] fileArr, @ModelAttribute("sdbseq") int sdbseq,SdboardDto sdboardDto, HttpSession session) throws IOException {
	      logger.info("[ajax] Ajax File Update : >>>>>>>>>>>>>>>>>>>>> fileArr : " + fileArr + " sdbseq : " + sdbseq);
	      logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + fileArr.length);
	      Map<String, Object> output = new HashMap<String, Object>();
	      output.put("msg", "fail");      // 디폴트 fail
	      
	      //회원의 email 주소 = id
	      String id = "";

	      // #1 유저id, 년, 월, 일 폴더 생성
	      String id_ymdPath = "";
	      SduserDto sduserDto = (SduserDto) session.getAttribute("login");
	      if(sduserDto != null) {
	         id = sduserDto.getSduemail();
	         // id_ymdPath 파일 경로 설정 >>> 유져id/년/월/일 
	         id_ymdPath = UploadFileUtils.calcPath(imgUploadPath, id);
	      }

	      // #2 파일 저장후 성공시 "view에서 불러올 경로 + 파일명" 배열에 담는다[썸내일 제외 원본이미지], 썸내일은 따로 "view에서 불러올 경로 + 파일명"을 String에 담는다. >> 향후 실패 처리시를 만들어야함
	      String[] imgNameArr = new String[fileArr.length];
	      String thumbImgName = "";

	      int j = 0;
	      for(MultipartFile file : fileArr) {
	         if(file.getSize() != 0) { // 파일이 있다면
	            if(j == 0) {
	               // 파일과 썸내일 생성 >> 썸내일이름[0], 원본파일이름[1] 배열로 리턴
	               String[] tempName =  UploadFileUtils.imgUploadAndThumb(imgUploadPath, file.getOriginalFilename(), file.getBytes(), id_ymdPath);
	               // DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + /s/ + / 썸내일 파일명
	               thumbImgName = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + "/s/"  + tempName[0];  
	               // DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + / + 파일명 
	               imgNameArr[0] = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + File.separator + tempName[1];
	               
	            } else {
	               // 파일 생성
	               String fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), id_ymdPath);
	               // DB에 저장할 경로 : /semi/resources/IMG/board/img + / + 유져id/년/월/일 + / + 파일명 
	               imgNameArr[j] = "/semi/resources/IMG/board/img" + File.separator + id_ymdPath + File.separator + fileName;
	            }
	            j++;
	         } 
	      }
	      // #3 DB저장 (id, 시퀀스_pk, 썸네일이미지명, 이미지명) 저장
	      sdboardDto.setSduemail(id);
	      sdboardDto.setSdbseq(sdbseq);
	      sdboardDto.setThumbnail(thumbImgName);
	      String imgNames = "";
	      for(int i = 0; i<imgNameArr.length; i++) {
	         if(i == 0) {
	            imgNames = imgNameArr[0];
	         } else {
	            imgNames = imgNames + "??" + imgNameArr[i];
	         }
	      }
	      sdboardDto.setSdbimgpath(imgNames);
	      
	      // **수정전 이전 Dto 가져오기
	      SdboardDto oldBoardDto = sdboardbiz.selectOne(sdbseq);
	      
	      int res = sdboardbiz.updateImg(sdboardDto);
	      
	      if(res > 0) {
	         logger.info("Board img 수정 성공");
	         
	         // #4  **기존에 있던 이미지 삭제
	         logger.info("board Update Res >>>>>>>>>>>>>>>> 수정후 글의 이전 이미지 모두 삭제");
	         String[] formerImgPathArr = oldBoardDto.getSdbimgpath().split("\\?\\?");   // .split() : 결과를 배열로 리턴한다. 문자열을 나눌 기준이 없을 경우 문자열을 그대로 배열의 0번지에 넣어 리턴한다.(즉 리턴되는 배열의 크기는  항상 1이상이다.)   
	                                   
	         // **파일 삭제 코드 : while문을 사용하여 파일삭제가 실패한 경우에 재실행 코드 구현
	         int i = 0;
	         while(i<formerImgPathArr.length) {
	            String fileName = Util.toAbsolutePath(formerImgPathArr[i], "C:\\GIT\\study\\SEMI_study\\src\\main\\webapp\\", 6);//db에 담긴 상대주소의 중복된 부분을 삭제~ 여기선 /semi/삭제 
	            if(Util.fileDelete(fileName)) {
	               i++;   // 파일 삭제 성공인 경우에만 i++ 실행
	            }
	         }
	         // #5 여기까지 성공 헀다면 output를 만들어 보낸다 
	         output.put("imgSrcArr", imgNameArr);
	         output.put("msg", "success");
	      }      
	      logger.info("[AjaxFileUpdate.do] >>>>>>>>>>>>>>>>>>>>>>> output : " + output);
	      return output;
	   }
	   
	   
	   @RequestMapping(value="/boardupdateres.do", method = RequestMethod.POST)
	   public String boardupdateres(Model model, @ModelAttribute SdboardDto sdboardDto, HttpSession session) throws IOException {
	      logger.info("board Update Res >>>>>>>>>>>>>>>>>>>>> " + sdboardDto);
	      
	      // #1 세션에서 id(=email) 찾기
	      SduserDto sduserDto = (SduserDto) session.getAttribute("login");
	      String id = "";
	      if(sduserDto != null) {
	         id = sduserDto.getSduemail();
	      } 
	      
	      // #2 첨부파일 업로드
	      MultipartFile[] fileArr = sdboardDto.getFile();
	      String fileNames = "";  
	      
	      if (fileArr[0].getSize() != 0) {   // 들어온 파일이 있다면
	         //폴더 생성 >> fileUploadPath + /id/yyyy/mm/dd/ >> 있으면 pass
	         String id_ymdPath = UploadFileUtils.calcPath(fileUploadPath, id);
	         
	         //파일 업로드
	         for(int i = 0; i<fileArr.length; i++) {
	            if (i == 0) {
	               String temp = UploadFileUtils.fileUpload(fileUploadPath, fileArr[i].getOriginalFilename(),  fileArr[i].getBytes(), id_ymdPath);
	               fileNames = fileUploadPath + id_ymdPath + File.separator + temp;
	            } else {
	               String temp = UploadFileUtils.fileUpload(fileUploadPath, fileArr[i].getOriginalFilename(),  fileArr[i].getBytes(), id_ymdPath);
	               fileNames = fileNames + "??" + fileUploadPath + id_ymdPath + File.separator + temp;
	            }
	         }
	      }
	      
	      // id(email), fileNames(업로드한 파일명) dto 추가
	      sdboardDto.setSduemail(id);
	      if(sdboardDto.getSdbfilpath() == null) {
	         // 업로드된 첨부 파일이 없다면 기존 경로를 사용
	    	  SdboardDto temp = sdboardbiz.selectOne(sdboardDto.getSdbseq());
	    	  sdboardDto.setSdbfilpath(temp.getSdbfilpath());
	      } else {
	         // 업로드된 첨부 파일이 있다면 업로드한 파일 경로를 새로 추가
	    	  sdboardDto.setSdbfilpath(fileNames);
	      }
	      
	      // content 안에 img태그가 없을 경우 >>> DB 이미지 썸내일 칼럼 삭제
	      if(!Util.isImgTag(sdboardDto.getSdubcontent())) {
	         int noImgUpdateRes = sdboardbiz.updateNoImgBoard(sdboardDto);
	         if(noImgUpdateRes > 0) {
	            logger.info("board Update Res >>>>>>>>>>>>>>>> [No img 수정 성공] Board update success");
	            return "redirect:/board.do?sdbseq=" + sdboardDto.getSdbseq();
	         } else {
	            logger.info("board Update Res >>>>>>>>>>>>>>>> [No img 수정 성공] Board update fail");
	            return "redirect:/boardupdate.do";
	         }
	      } else {
	         // 이미지 있을경우 DB 수정 
	         SdboardDto oldBoardDto = sdboardbiz.selectOne(sdboardDto.getSdbseq());
	         logger.info("xxxxxxxxxxxxxxxxxx>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + oldBoardDto);
	         int res = sdboardbiz.updateBoard(sdboardDto);
	         if(res > 0) {
	            logger.info("board Update Res >>>>>>>>>>>>>>>> [글 수정 성공] Board update success");
	            return "redirect:/board.do?sdbseq=" + sdboardDto.getSdbseq();
	         } else {
	            logger.info("board Update Res >>>>>>>>>>>>>>>> [글 수정 실패] Board update fail");
	            model.addAttribute("boardDto",sdboardDto);
	            return "redirect:/boardupdate.do";
	         }
	      }
	   }
	
////////////////////////////////////////////////////////////	
	
	
	

}
