package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.SigninDTO;
import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.handler.exception.RedirectException;
import com.example.demo.repository.interfaces.UserRepository;
import com.example.demo.repository.model.User;
import com.example.demo.utils.Define;
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	  * @Method Name : createUser
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 회원가입 서비스 처리
	  */
	@Transactional
	public void createUser(SignUpDTO dto) {
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.fullname(dto.getFullname())
				.build();
		
		int result = 0;
		try {
			result = userRepository.insert(user);
			// 여기서 예외 처리를 하면 상위 catch 블록에서 예외를 잡는다. 
		} catch (DataAccessException e) {
			// DataAccessException는 Spring의 데이터 액세스 예외 클래스로,
			// 데이터베이스 연결이나 쿼리 실행과 관련된 문제를 처리합니다.
			throw new DataDeliveryException(Define.INVALID_INPUT,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// 그 외 예외 처리 - 페이지 이동 처리 
			throw new RedirectException(Define.UNKNOWN , HttpStatus.SERVICE_UNAVAILABLE);
		}
		if (result != 1) {
			throw new DataDeliveryException(Define.FAIL_TO_CREATE_USER, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	  * @Method Name : signIn
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 로그인 서비스 처리
	  */
	public User signIn(SigninDTO dto) {
		// 유효성 검사는 Controller 에서 먼저 하자
		User userEntity = null;
		
		try {
			userEntity = userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.INVALID_INPUT,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// 예외 처리 - 에러 페이지 호출 
			throw new RedirectException(Define.UNKNOWN , HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		// 예외 클래스 발생 안됨. 프로세스 입장에서 예외로 처리 throw 처리 함 
		if(userEntity == null) {
			throw new DataDeliveryException("아이디 혹은 비번이 틀렸습니다",
					HttpStatus.BAD_REQUEST);
		}
		
		return userEntity;
	}
	
	
	
	
	
	
	
}
