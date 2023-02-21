package com.fullstack.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.repository.GuestbookRepository;

@SpringBootTest
public class GuestbookServiceTests {

	@Autowired
	//private GuestbookRepository guestbookRepository;
	private GuestbookService guestbookService;
	
	@Test
	public void registerTest() {
		
		//DTO를 생성해서 service 개즐에 넘깁니다
		GuestbookDTO guestbookDTO = GuestbookDTO.builder().title("이건 신규 등록").content("신규등록 글내용").writer("심지석").build();
		System.out.println(guestbookService.register(guestbookDTO));
		
	}
	
}
