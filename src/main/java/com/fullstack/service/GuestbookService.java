package com.fullstack.service;

import com.fullstack.dto.GuestbookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Guestbook;

public interface GuestbookService {

	//글목록을 리턴하는 메서든 선언합니다
	//글목록을 담는 DTO는 PageResult이기 떄문에 PageResultDTO를 리턴하도록합니다
	PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
	
	default GuestbookDTO entityToDto(Guestbook entity) {
		
		GuestbookDTO dto = GuestbookDTO.builder().gno(entity.getGno()).title(entity.getTitle()).content(entity.getContent())
							.writer(entity.getWriter()).regDate(entity.getRegDate()).modDate(entity.getModDate()).build();
		return dto;
	}
	
	
	//이 서비스 층에서 게시판의 사용자가 요청하는 로직을 모두 처리한다고 보면 됩니다
	//떄문에 필요한 모든 기능은 여기에 선언 또는 default를 이용해서
	//정의 후 하이타입의 구현 객체에 상속을 시켜주시면, 해당 객체를 빈으로 생성해서
	//실제 비즈니스로직을 처리하게 됩니다
	
	//신규 글을 등록하도록 하는 메서드를 선언 합니다
	//기본 로직은 신규 글이 등록되면 해당 글번호(gno)를 리턴하도록 합니다
	Long register(GuestbookDTO dto);
	
	/*
	 * 일반적으로 DTO와 Entity는 같은 구성을 가지게 됩니다
	 * 만약 엔티티 수만큼 DTO를 생성하게 되면 중복 코드를 계속 정의하게 되니 효율성이 떨어집니다. 
	 * 위 같은 문제를 처리하기위한 프러그인도 있지만 그냥 간단한 로직을 적용해서 
	 * DTO----->entity, entity----->DTO로 정의 해봅니다. 그냥 값만 카피해서 리턴하면 됩니다
	 */
	
	default Guestbook dtoToEntity(GuestbookDTO dto) {
		
		Guestbook entity = Guestbook.builder()
				.gno(dto.getGno()).title(dto.getTitle()).content(dto.getContent()).writer(dto.getWriter()).build();
		return entity;
		
	}
	
	//글상세 메서드 선언
	GuestbookDTO read(Long gno);
	
}
