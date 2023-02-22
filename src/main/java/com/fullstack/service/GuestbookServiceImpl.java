package com.fullstack.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
		
		//PageRequestDTO가 하는일
		//Pageable 객체를 통해서 페이지와 소트, 목록등을 얻어냈습니다
		//그래서 Pageable 객체를 얻어내고 기본 sort를 적용합니다
		
		Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
		//repository를 통해 pageable 파라미터로 Page Entity 리스트를 얻어냅니다
		Page<Guestbook> result = guestbookRepository.findAll(pageable);
		
		//entity를 dto로 변환 하도록 Function 객체 생성
		Function<Guestbook, GuestbookDTO> fn = (entity->entityToDto(entity));
		
		//PageResultDTO 객체생성
		return new PageResultDTO<>(result, fn);
		
	}
	
	@Override
	public GuestbookDTO read(Long gno) {
		Optional<Guestbook> result = guestbookRepository.findById(gno);
		
		return result.isPresent()?entityToDto(result.get()):null;
	}
		
}
