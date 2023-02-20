package com.fullstack.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guestbook extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gno;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(length = 1000, nullable = false)
	private String content;
	
	@Column(length = 100, nullable = false)
	private String writer;
	
}
