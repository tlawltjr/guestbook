package com.fullstack.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Guestbook;
import com.fullstack.repository.GuestbookRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
public class GuestbookServiceTests {

	@Autowired
	//private GuestbookRepository guestbookRepository;
	private GuestbookService guestbookService;
	
//	@Test
//	public void registerTest() {
//		
//		//DTO를 생성해서 service 개즐에 넘깁니다
//		GuestbookDTO guestbookDTO = GuestbookDTO.builder().title("이건 신규 등록").content("신규등록 글내용").writer("심지석").build();
//		System.out.println(guestbookService.register(guestbookDTO));
//		
//	}
	@Test
	public void Test() {
	/*
	 * 글목록 얻어내기 테스트
	 */
	//PageRequestDTO는 직접생성해서 던져줍니다
//	PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
//	
//	//PageResultDTO 객체를 생성해서 조회된 리스트 확인합니다
//	PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
//	
//	for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
//		System.out.println(guestbookDTO);
//	}
//	System.out.println("TOTAL : " + resultDTO.getTotalPage());
	
	}
	//글목록에서 페이징 처리된 내용 알아보기
	@Test
	public void pagingTest() {
		
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).size(10).build();
		PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
		
		 System.out.println("이전 표시 : " + resultDTO.isPrev());
		 System.out.println("다음 표시 : " + resultDTO.isNext());
		 System.out.println("실제 총 페이지 수 : " + resultDTO.getTotalPage());
		 
		 System.out.println("페이지 리스트 확인 +++++++++++++");
		 
		 resultDTO.getPageList().forEach(i->System.out.println());
	}
	

}
