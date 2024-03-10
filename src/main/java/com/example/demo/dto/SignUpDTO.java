package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDTO {
	
	private String username;
	private String password;
	private String fullname;
	
//  ToDo 추후 진행 예정 	
//	private MultipartFile customFile; // name 속성과 일치 시켜야 함
//	private String originFileName; 
//	private String uploadFileName; 
//	private String eMail;
}

