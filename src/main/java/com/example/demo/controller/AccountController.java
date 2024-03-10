package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AccountSaveDTO;
import com.example.demo.dto.DepositDTO;
import com.example.demo.dto.WithdrawalDTO;
import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.handler.exception.UnAuthorizedException;
import com.example.demo.repository.model.Account;
import com.example.demo.repository.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.utils.Define;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountService accountService;
	
	/**
	  * @Method Name : savePage
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 계좌 생성 페이지 요청
	  */
	@GetMapping("/save")
	public String savePage() {
		User principal = (User)session.getAttribute("principal");
		if(principal == null) {
			throw new UnAuthorizedException("인증된 사용자가 아닙니다", 
					HttpStatus.UNAUTHORIZED);
		}  
		
		return "account/save";
	}
	
	
	/**
	  * @Method Name : saveProc
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 계좌 생성 서비스 처리
	  */
	@PostMapping("/save")
	public String saveProc(AccountSaveDTO dto) {
		// 유효성 검사보다 인증검사를 먼저 하는 것이 좋습니다.
		
		// 1. 인증검사 
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN, 
					HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사 
		if(dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getBalance() == null || 	dto.getBalance() <= 0 ) {
			throw new DataDeliveryException(Define.INVALID_INPUT, 
					HttpStatus.BAD_REQUEST);
		}
		accountService.createAccount(dto, principal.getId());
		
		// TODO 추후 account/list 페이지가 만들어 지면 수정 할 예정 입니다.
		return "redirect:/account/list"; 
	}
	
	/**
	 * 계좌 목록 페이지
	 * 
	 * @param model - accountList
	 * @return list.jsp
	 */
	@GetMapping("/list")
	public String listPage(Model model) {
		
		// 1.인증 검사가 필요(account 전체 필요)
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		if (principal == null) {
			throw new UnAuthorizedException(Define.NOT_AN_AUTHENTICATED_USER, HttpStatus.UNAUTHORIZED);
		}
		
		// 경우의 수 -> 유, 무
		List<Account> accountList = accountService.readAccountListByUserId(principal.getId());
		
		if (accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList);
		}
		
		return "account/list";
	}
	
	
	/**
	 * 출금 페이지 요청 
	 * @return withdraw.jsp
	 */
	@GetMapping("/withdrawal")
	public String withdrawalPage() {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		if (principal == null) {
			throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN, HttpStatus.UNAUTHORIZED);
		}
		return "account/withdrawal";
	}
	
	/**
	 * 출금 요청 기능 처리  
	 * @return account/list.jsp
	 */
	@PostMapping("/withdrawal")
	public String withdrawalProc(WithdrawalDTO dto) {
		
		return "redirect:/account/list";
	}
	
	@GetMapping("/deposit")
	public String depositPage() {
		// 1. 인증 검사
		User principal = (User) session.getAttribute(Define.PRINCIPAL); // 다운 캐스팅
		if (principal == null) {
			throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN, HttpStatus.UNAUTHORIZED);
		}
		return "account/deposit";
	}
	
	@PostMapping("/deposit")
	public String depositProc(DepositDTO dto) {
		// 1. 인증 검사
		User principal = (User) session.getAttribute(Define.PRINCIPAL); // 다운 캐스팅
		if (principal == null) {
			throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN, HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}
		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.D_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}
		if (dto.getDAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출
		accountService.updateAccountDeposit(dto, principal.getId());

		return "redirect:/account/list";
	}
	
}
