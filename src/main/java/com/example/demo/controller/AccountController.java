package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dto.AccountSaveDTO;
import com.example.demo.dto.DepositDTO;
import com.example.demo.dto.HistoryAccountDTO;
import com.example.demo.dto.TransferDTO;
import com.example.demo.dto.WithdrawalDTO;
import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.handler.exception.UnAuthorizedException;import com.example.demo.repository.interfaces.HistroyRepository;
import com.example.demo.repository.model.Account;
import com.example.demo.repository.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
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
	public String saveProc(AccountSaveDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {
		// 유효성 검사보다 인증검사를 먼저 하는 것이 좋습니다.
		
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
	// Spring MVC에서 제공하는 @SessionAttribute 어노테이션을 사용해서
	// 메서드의 파라미터로 주입받을 수 있습니다.
	@GetMapping("/list")
	public String listPage(Model model, @SessionAttribute(Define.PRINCIPAL) User principal) {
		
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
		
		return "account/withdrawal";
	}
	
	/**
	 * 출금 요청 기능 처리  
	 * @return account/list.jsp
	 */
	@PostMapping("/withdrawal")
	public String withdrawalProc(WithdrawalDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {
		
		// 유효성 검사
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.W_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getWAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}

		if (dto.getWAccountPassword() == null || dto.getWAccountPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccountWithdraw(dto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	@GetMapping("/deposit")
	public String depositPage() {
		
		return "account/deposit";
	}
	
	@PostMapping("/deposit")
	public String depositProc(DepositDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {
		
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
	
	
	/**
	 * 계좌 이체 화면 요청 
	 * @return transfer.jsp 
	 */
	@GetMapping("/transfer")
	public String transferPage() {
		
		
		return "account/transfer";
	}
	
	/**
	 * 계좌 이체 기능 구현 
	 * @param TransferDTO 
	 * @return redirect:/account/list
	 */
	@PostMapping("/transfer")
	public String transferProc(TransferDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {
		

		// 2. 유효성 검사
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}
		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.D_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}
		if (dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new DataDeliveryException("출금하실 계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new DataDeliveryException("이체하실 계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (dto.getDAccountNumber().equals(dto.getWAccountNumber())) {
				throw new DataDeliveryException("같은 계좌로는 이체할 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출
		accountService.updateAccountTransfer(dto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	
	/**
	 * 계좌 상세 보기 요청 
	 * @param accountId 
	 * @param type = ["all", "deposit", "withdrawal"] 
	 * @return account/detail.jsp 
	 */
	@GetMapping("/detail/{accountId}")
	public String detail(@PathVariable Integer accountId, @RequestParam(name = "type", defaultValue = "all", required = false) String type, Model model
			,@SessionAttribute(Define.PRINCIPAL) User principal) {
		
		// 2. 유효성 검사
		List<String> validTypes = Arrays.asList("all", "deposit", "withdrawal"); // 허용되는 type 값들
		if (!validTypes.contains(type)) {
			throw new DataDeliveryException("유효하지 않는 접근 입니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 화면을 구성하기위해 필요한 데이터 
		// 소유자 이름 - account_tb 
		// 계좌 번호(1개), 현재 계좌 잔액 - account_tb   
		// 거래 내역  - history_tb 
		
		Account account = accountService.readAccountById(accountId);
		List<HistoryAccountDTO> historyList = accountService.readHistoryByAccountId(type, accountId);
		
		// 데이터 JSP 내려 주기
		model.addAttribute(Define.PRINCIPAL, principal);
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);
		
		return "/account/detail";
	}
	
	
}
