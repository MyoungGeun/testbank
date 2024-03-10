package com.example.demo.dto;

import lombok.Data;

@Data
public class WithdrawalDTO {
	private Long amount;
	private String wAccountNumber;
	private String wAccountPassword;
}
