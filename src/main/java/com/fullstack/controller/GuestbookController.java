package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.service.GuestbookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping({"/guestbook"})
@Log4j2
//서비스 구현체를 DI시키기 위해서 프레임 워크에 @argmentsConst..선언
@RequiredArgsConstructor
public class GuestbookController {
	
	//서비스 구현체 선언 합니다
	private final GuestbookService service;
	
	//context로 요청이 올때 요청처리. ex>guestbook/ 이렇게 요청온 경우 리다이렉션함
	@GetMapping("/")
	public String index() {
		return "redirect:/guestbook/list";
	}
	/*
	 * 아래 list의 파라미터로 RequestDTo를 선언하게 되면,
	 * 프레임 워크가 기본적으로 사용자의 요청 파라미터를 dto에 맵핑해줍니다
	 * ex>page number등 그래서 파라미터로 정의한 RequestDTo를 주고,
	 * service 계층에서 처리된 목록 리스트를 View단으로 전달하기 위해 Model파라미터도 선언
	 */
	@GetMapping({"/list"})
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("리스트 페이지 요청이 처리됨");
		
		model.addAttribute("list",service.getList(pageRequestDTO));
		
	}
	
	@PostMapping("/register")
	public String register(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
		log.info("등록 요청");
		Long number = service.register(dto);
		log.info("엔티티의 신규 글 번호 : " + number);
		
		//신규 글 작성이 완료된 후 list로 redirect시키는데,
		//list 페이지에서는 글작성 후인지 그냥 글목록을 보는지 모름
		//따라서 list 페이지에선 여기서 넘겨주는 key값을 판별후
		//동적으로 alert()를 생성해서 사용자에게 고지할 예정입니다.
		redirectAttributes.addFlashAttribute("msg", number);
		return "redirect:/guestbook/list";
	}
	
	@GetMapping("/register")
	public void getRegister() {
		log.info("등록 요청");
	}
	
	@GetMapping("/read")
	public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
		log.info("Read 처리");
		GuestbookDTO dto = service.read(gno);
		
		model.addAttribute("dto", dto);
	}
	
	@PostMapping("/modify")
	public String pModify(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
		log.info("수정 요청");
		Long number = service.modify(dto);
		
		redirectAttributes.addFlashAttribute("msg", number);
		return "redirect:/guestbook/list";
	}

	@GetMapping("/modify")
	public void modify(long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
		log.info("수정 요청");
		GuestbookDTO dto = service.read(gno);
		
		model.addAttribute("dto", dto);
	}
	
	@PostMapping("/remove")
	public String remove(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
		log.info("삭제 요청");
		Long number = service.remove(dto);
		
		redirectAttributes.addFlashAttribute("msg", number);
		return "redirect:/guestbook/list";
	}
	
	
	
	
	
	
//	//view 페이지명을 리턴하는 메서드 정의
//	//String타입 매서드는 viewer 호출
//	@GetMapping({"/","/list"})
//	public String list() {
//		log.info("방명록 테스트 리스트 페이지 호출");
//		return "/guestbook/list";
//		
//		
//	}
	
}
