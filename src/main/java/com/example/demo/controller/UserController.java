package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.SigninDTO;
import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.repository.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Define;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	/**
	  * @Method Name : signUpPage
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 회원가입 페이지 요청
	  */
	@GetMapping("/sign-up")
	public String signUpPage() {
		//   prefix: /WEB-INF/view/
		//   suffix: .jsp
		
		return "user/signUp";
	}
	
	/**
	  * @Method Name : signProc
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 회원 가입 요청 처리
	  */
	@PostMapping("/sign-up")
	public String signProc(SignUpDTO dto) {
		
		// 1. 인증검사 x 
		// 2. 유효성 검사 
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME
					, 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_FULLNAME, 
					HttpStatus.BAD_REQUEST);
		}		
		userService.createUser(dto);
		
		// TODO 로그인 페이지로 변경 예정
		return "redirect:/user/sign-in";
	}
	
	/**
	  * @Method Name : signInPage
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 로그인 페이지 요청
	  */
	@GetMapping("/sign-in")
	public String signInPage() {
		// 인증 검사 불필요 
	    //   prefix: /WEB-INF/view/
	    //   suffix: .jsp
		return "user/signin";
	}
	
	/**
	 * 로그인 처리 
	 * @param signInFormDto
	 * @return 메인 페이지 이동 (수정 예정)
	 * 생각해보기 
	 * GET 방식 처리는 브라우저 히스토리에 남겨지기 때문에 
	 * 예외적으로 로그인 POST 방식으로 처리 한다. (보안) 
	 */
	@PostMapping("/sign-in")
	public String signInProc(SigninDTO dto) {
		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME, HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || 
				dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		User principal = userService.signIn(dto);
		
		session.setAttribute(Define.PRINCIPAL, principal);
		
		return "redirect:/main-page";
	}
	
	/**
	  * @Method Name : logout
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 로그아웃 후 로그인 페이지 요청
	  */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}
}
