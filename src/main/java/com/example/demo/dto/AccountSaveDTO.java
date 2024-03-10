package com.example.demo.dto;

import com.example.demo.repository.model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSaveDTO {
	private String number;
	private String password;
	private Long balance;
	
	// 세션 정보에 사용자 ID를 받아서 Account 를 생성 해야 한다.
	public Account toAccount(int principalId) {
		return Account.builder()
				.number(this.number)
				.password(this.password)
				.balance(this.balance)
				.userId(principalId)
				.build();
	}
}
