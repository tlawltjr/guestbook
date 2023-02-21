package com.fullstack.dto;

import java.util.List;


import lombok.Data;

/*
 * 이 클래스는 요청된 페이지에 해당하는 글 목록을 가져오는 DTO입니다
 * 
 * 조회된 글은 컬렉션으로 처리되어 리턴하도록 합니다
 */
@Data
public class PageResultDTO<DTO, EN> {

	//글 목록 담은 컬렉션 선언
	private List<DTO> dtoList;
	
	//public PageResultDTO<DTO, EN>(){
		
	//}
	
}
