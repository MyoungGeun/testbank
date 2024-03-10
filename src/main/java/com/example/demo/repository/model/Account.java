package com.example.demo.repository.model;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.example.demo.handler.exception.DataDeliveryException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.example.demo.utils.Define;
//Account Entity 를 설계 중입니다.
//Enitity 로 사용하는 클래스는 로직을 포함 할 수 있다.  
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
//슈퍼클래스의 equals와 hashCode를 호출하지 않음을 명시
public class Account {
	private Integer id; 
	private String number; 
	private String password; 
	private Long balance; 
	private Integer userId;
	private Timestamp createdAt; 


	// 출금 기능 
	public void withdraw(Long amount) {
		// 방어적 코드 작성 예정 
		this.balance -= amount;
	}
	// 입금 기능 
	public void deposit(Long amount) {
		this.balance += amount;
	}
	
	// 패스워드 체크
	public void checkPassword(String password) {
		if (this.password.equals(password) == false) {
			throw new DataDeliveryException(Define.FAIL_ACCOUNT_PASSWROD, HttpStatus.BAD_REQUEST);
		}
	}

	// 잔액 여부 확인 기능
	public void checkBalacne(Long amount) {
		if (this.balance < amount) {
			throw new DataDeliveryException(Define.LACK_Of_BALANCE, HttpStatus.BAD_REQUEST);
		}
	}

	// 계좌 소유자 확인 기능
	public void checkOwner(Integer principalId) {
		if (this.userId != principalId) {
			throw new DataDeliveryException(Define.NOT_ACCOUNT_OWNER, HttpStatus.BAD_REQUEST);
		}
	}
}
