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
import com.fullstack.entity.QBaseEntity;
import com.fullstack.entity.QGuestbook;
import com.fullstack.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

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
		//조회조건이 없는 상태의 list페이지 목록을 리턴하는 메서드
		//Page<Guestbook> result = guestbookRepository.findAll(pageable);
		
		//조회조건을 추가하여 list페이지의 목록을 리턴하는 구문
		BooleanBuilder booleanBuilder = getFind(requestDTO);
		Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
		
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
	@Override
	public Long modify(GuestbookDTO dto) {
		log.info("-----나와라-----");
		log.info(dto);
		
		//DTO --> Entity로 변환되는 부모메서드 호출합니다
		Guestbook entity = dtoToEntity(dto);
		
		log.info("변환된 엔티티 정보-------->" + entity);
		guestbookRepository.save(entity);
		return entity.getGno();
	}
	@Override
	public Long remove(GuestbookDTO dto) {
		Guestbook entity = dtoToEntity(dto);
		
		log.info("변환된 엔티티 정보-------->" + entity);
		guestbookRepository.delete(entity);
		return entity.getGno();
	}
	//검색 조건을 추가하여, 검색에 매칭되는 Entity를 구성해서 getListPage()로 보낸다
	//QueryDSL을 이용할 예정이라 리턴타입은 javax.persistant.page 객체로 
	//리턴시키기 위해서 BooleanBuilder 객체로 리턴할 예정
	//이렇게 리턴된 BooleanBuilder를 findAll(BooleanBuilder, Pageable)
	//메서드를 통해 Page객체를 얻어내서 list.html 까지 전달 시킨다.
	//QueryDSL의 장점 : Entity 필드를 조회 조건으로 이용할 수 있다
	private BooleanBuilder getFind(PageRequestDTO pageRequestDTO) {
		//사용자가 요청한 검색 키워드 알아내기
		String type = pageRequestDTO.getType();//c,w,t 거나 모두이거나
		
		QGuestbook qGuestbook = QGuestbook.guestbook;
		//QBaseEntity a = QBaseEntity.baseEntity;
		String keyword = pageRequestDTO.getKeyword();
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		//검색조건을 생성하는데, 기본적으로 gno를 기준으로 먼저 검색조건을 생성합니다
		BooleanExpression booleanExpression = qGuestbook.gno.gt(0L);
		//생성된 조회건을 booleanbuilder에 추가합니다
		//이유는 repository.find()에 들어갈 파라미터는 booleanBuilder 객체이기떄문입니다
		booleanBuilder.and(booleanExpression);
		//만약 검색조건이 아무것도 없다면 , 일반 조회조건 즉, gno > 0인 애들을 돌려주도록 합니다
		if(type == null || type.equals("") || type.isEmpty() || type.length() == 0){
			return booleanBuilder;
		}
		
		//아래서 사용될 조회 조건을 담는 BooleanBuilder 또 하나 생성
		BooleanBuilder findbuilder = new BooleanBuilder();
		
		//어떤 필드(QDomain)에서 keyword를 찾아야 할지 요청 타입(type)을 검색해 봅니다
		//만약 아무런 type 조건이 없을 경우, 그냥 글번호를 기준으로 넘겨주도록 합니다
		if(type.contains("t")) {
			//모든 조건을 or 로 묶어서 추가합니다
			findbuilder.or(qGuestbook.title.contains(keyword));
		}
		if(type.contains("c")) {
			findbuilder.or(qGuestbook.content.contains(keyword));
		}
		if(type.contains("w")) {
			findbuilder.or(qGuestbook.writer.contains(keyword));
		}
		//위 조건 검색을 하나로 통함
		booleanBuilder.and(findbuilder);
		
		return booleanBuilder;
	}
	
		
}
