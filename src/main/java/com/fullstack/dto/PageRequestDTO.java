package com.fullstack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
 * 이 DTO는 뷰단에서 사용자의 페이지 요청 시에 데이터를 담아 전달되는 DTO입니다
 */
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
	
	//요청되는 페이지 번호
	private int page;
	private int size;
	
	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
		
	}
	//이후 추가 메서드는 페이지 겨로가 처리 DTO를 정의 후에 재정의합니다.
	
	
}
