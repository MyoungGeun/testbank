package com.example.demo.dto;

import com.example.demo.repository.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SigninDTO {
	
	private String username;
	private String password;
	
	public User toUser() {
		return User.builder()
				.username(this.username)
				.password(this.password)
				.build();
	}
}
