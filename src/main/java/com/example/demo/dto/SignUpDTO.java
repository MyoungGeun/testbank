package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.model.User;

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
	// name 속성과 일치 시켜야 함 - 다중 처리는 MultipartFile[] 배열 활용 
	private MultipartFile mFile;  
	private String originFileName; 
	private String uploadFileName; 
	
	// 2단계  
    public User toUser() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .fullname(this.fullname)
                .originFileName(originFileName)
                .uploadFileName(uploadFileName)
                .build();
    }
}

