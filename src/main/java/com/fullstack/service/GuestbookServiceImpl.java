package com.fullstack.service;

import org.springframework.stereotype.Service;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Guestbook;
import com.fullstack.repository.GuestbookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/*
 * 이 하위타입의 빈(Bean) 객체를 생성(처리) 되도록 프레임워크에 등록 시킵니다
 * @Service
 * 실행중에 자동의 해당 인스턴스를 생성해서 주입시켜줍니다. 이걸 Bean이라고 합니다.
 */
@Service
@Log4j2
//final 속성의 객체를 주입하는 어노테이션 RequiredArgsConstructor
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

	private final GuestbookRepository guestbookRepository;
	@Override
	public Long register(GuestbookDTO dto) {
		
		log.info("-----나와라-----");
		log.info(dto);
		
		//DTO --> Entity로 변환되는 부모메서드 호출합니다
		Guestbook entity = dtoToEntity(dto);
		
		log.info("변환된 엔티티 정보-------->" + entity);
		guestbookRepository.save(entity);
		return entity.getGno();
	}
	@Override
	public PageResultDTO<PageRequestDTO, Guestbook> getList(PageRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
		
}
