package com.update.semi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.update.semi.kakaoapi.KakaoUserInfo;
import com.update.semi.kakaoapi.kakao_restapi;

@Controller
public class KakaoController {

	private kakao_restapi kakaoRestApi = new kakao_restapi();
	private KakaoUserInfo kakaoUserInfo = new KakaoUserInfo();
	
	@RequestMapping(value = "/oauth", produces = "application/json", 
			method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code") String code) {
		System.out.println("여기는 kakao컨트롤러 받아온 코드값은? "+code);
        JsonNode accessToken;
        
        // JsonNode트리형태로 토큰받아온다
        JsonNode jsonToken = kakaoRestApi.getAccessToken(code);
        // 여러 json객체 중 access_token을 가져온다
        accessToken = jsonToken.get("access_token");
 
        System.out.println("access_token : " + accessToken);
        
        
        //???????????????????
        // access_token을 통해 사용자 정보 요청
        JsonNode userInfo = kakaoUserInfo.getKakaoUserInfo(accessToken);
 
        // Get id
        String id = userInfo.path("id").asText();
        String name = null;
        String email = null;
 
        // 유저정보 카카오에서 가져오기 Get properties
        JsonNode properties = userInfo.path("properties");
        JsonNode kakao_account = userInfo.path("kakao_account");
 
        name = properties.path("nickname").asText();
        email = kakao_account.path("email").asText();
 
        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("email : " + email);

		
		return "home";
	}
	

	
}
