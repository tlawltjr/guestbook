package com.fullstack.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fullstack.entity.Guestbook;
import com.fullstack.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestBookRepositoryTests {

	//repository 를 필드로 선언합니다. 모든 JPA 작업은 이 필드의
	//save, delete, findxx() 를 통해 적용합니다.
	
	//객체 자동주입 어노테이션 사용합니다.
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	//하위 메서드에서는 Guestbook 테이블에 데이터를 약 300 개 정도 Insert 시킵니다.
//	@Test
//	public void insertDummies()
//		
//		//스트림이용해서 한번에 적용함.
//		IntStream.rangeClosed(1, 300).forEach(i->{
//			Guestbook guestbook = Guestbook.builder()
//					.title("이건 타이틀 .." + i)
//					.content("이건 컨테트(내용)..." + i)
//					.writer("심지석..." + i)
//					.build();
//			//수행 결과 콘솔 찍어보기..
//			System.out.println(guestbookRepository.save(guestbook));
//		});
//	}
	
	//GuestBook Entity의 setter를 통해 작성자와 내용을 update합니다
	//물론 알겠지만 해당 엔티티가 존재하는 지 확인 후 맞다면 내용만 바꾸기 때문에
	//save()를 호출해도 변겨오딘 내용만 갱신됩니다(JPA)
//	@Test
//	public void updateTest() {
//		//먼저 지정된 gno에 해당하는 애가 있는지 확인(Select)합니다
//		//java.util.Optional을 리턴받습니다(Optional)은 한번 찾아보세요
//		//전에 강의는 했지만 스프링에서 많이 쓰는 객체이니 확인해보세요
//		Optional<Guestbook> result = guestbookRepository.findById(300L);
//		//isPresent()는 존재할 경우 true 리턴함
//		if(result.isPresent()) {
//			Guestbook guestbook = result.get();
//			guestbook.changeWriter("심지석");
//			
//			
//			guestbookRepository.save(guestbook);
//		}
//	}
	
	/*
	 * QueryDSL 사용법 : 이 플러그인은 조인이나 조건등을 기존 JQA 보다 간편하게 
	 * 사용할 수 있도록 합니다.
	 * 
	 * 사용법은 아래와 같습니다
	 * 1.BooleanBuilder 객체를 생성합니다
	 * 	1_1.먼저 할 일은 동적 처리를 위해 QS 도메인 클래스를 얻어냅니다
	 * 	이렇게 얻어내면 엔티티 내에 있는 필드를 변수로 활용할 수 있습니다
	 * 	1_2.BooleasnBuilder는 where 조건문을 담아두는 객체입니다
	 * 	1_3.조건에 필요한 필드등을 조합해서 생성 후  Predicate 로 생성하여 넣어줍니다
	 * 
	 * 2.QueryDSL에서 사용하는 Predicate 타입의 함수를 생성해서 조건에 맞는 구문을 생성합니다
	 * 3.BooleanBuilder에 작성된 Predicate를 추가 후 실행합니다
	 * 
	 * 참고로 Predicate 는 java.util에도 있는 interface입니다.
	 * QueryDAL에도 같은 이름이 있으니 주의하세요.
	 */
//	@Test
//	public void testQuery() {
//		
//		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//		
//		String keyword = "이건";
//		//QS도메인을 생성합니다 생성된 Q클래스를 이용하면 됩니다
//		QGuestbook qGuestbook = QGuestbook.guestbook;
//		
//		//where 절이 들어가는 컨테이너 생성
//		BooleanBuilder builder = new BooleanBuilder();
//		
//		//원하는 조건 값을 qGuestbook의 필드를 통해 QueryDSL의 메서드를 이용하여
//		//조회 조건으로 생성하세요
//		BooleanExpression expression = qGuestbook.content.contains(keyword);
//		
//		builder.and(expression);
//		
//		Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//		
//		result.stream().forEach(guestbook->{
//			System.out.println(guestbook);
//		});
//				
//	}
//	@Test
//	public void updateTest() {
//		//먼저 지정된 gno에 해당하는 애가 있는지 확인(Select)합니다
//		//java.util.Optional을 리턴받습니다(Optional)은 한번 찾아보세요
//		//전에 강의는 했지만 스프링에서 많이 쓰는 객체이니 확인해보세요
//		List<Guestbook> result = guestbookRepository.findAll();
//		//isPresent()는 존재할 경우 true 리턴함
//			Guestbook guestbook = result.get(i);
//			guestbook.changeWriter("심지석");
//			
//			
//			guestbookRepository.save(guestbook);
//		
//	}
//	@Test
//	public void testConditionQuery() {
//		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//		String keyword = "1";
//		QGuestbook qGuestbook = QGuestbook.guestbook;
//		BooleanBuilder builder = new BooleanBuilder();
//		BooleanExpression exContent = qGuestbook.content.contains(keyword);
//		BooleanExpression exTitle = qGuestbook.content.contains(keyword);
//		
//		BooleanExpression exAll = exTitle.or(exContent);
//		
//		builder.and(exAll);
//		
//		//여기에 특정 조건을 더 추가 해볼게요 뭐뭐보다 크냐 작냐 등의 
//		//논리연산식을 적용할 수 있는데 문제는 gt lt등으로 되어있습니다
//		builder.and(qGuestbook.gno.lt(100L));
//		
//		Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//		
//		result.stream().forEach(guestbook->{
//			System.out.println(guestbook);
//		});
//		
//		
		
		
		
		

	
	
	
	
	
	
	

	
}
