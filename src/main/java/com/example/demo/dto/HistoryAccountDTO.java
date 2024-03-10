package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

//model 패키지가 아닌 DTO 패키지에 넣는 이유는 왜 일까? 
@Data
@EqualsAndHashCode(callSuper = false)
//슈퍼클래스의 equals와 hashCode를 호출하지 않음을 명시
public class HistoryAccountDTO {
	private Integer id;
	private Long amount; 
	private Long balance;
	private String sender; 
	private String receiver; 
	private Timestamp createdAt;
}
