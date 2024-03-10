package com.example.demo.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

// Data로 예외를 내려 줄 때 사용할 예정 
@Getter
public class DataDeliveryException extends RuntimeException {

	private HttpStatus status;

	public DataDeliveryException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
}
