package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping({"/guestbook"})
@Log4j2
public class GuestbookController {
	
	//view 페이지명을 리턴하는 메서드 정의
	//String타입 매서드는 viewer 호출
	@GetMapping({"/","/list"})
	public String list() {
		log.info("방명록 테스트 리스트 페이지 호출");
		return "/guestbook/list";
	}
	
}
