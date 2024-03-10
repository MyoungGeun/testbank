package com.example.demo.repository.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer id; 
	private String username;
	private String password; 
	private String originFileName; 
	private String uploadFileName;
	private String fullname; 
	private Timestamp createdAt; 
	
	public String setUpUserImage() {
		return uploadFileName == null ? "https://picsum.photos/id/1/350" : "/images/uploads/" + uploadFileName;
	}
}
