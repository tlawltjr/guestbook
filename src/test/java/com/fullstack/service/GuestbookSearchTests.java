package com.fullstack.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Guestbook;

@SpringBootTest
public class GuestbookSearchTests {
	
	@Autowired
	private GuestbookService service;
	//검색 기능의 단위 테스트 
	@Test
	public void  findKeywordTest() {
		//PageRequest객체 생성
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).type("t").keyword("모달").build();
		PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
		System.out.println("+++++++++++++++++++++조건 조회+++++++++++++++++++++++++++");
		System.out.println("total : " + resultDTO.getTotalPage());
		
		for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
			System.out.println(guestbookDTO);
		}
	}
	
}
