package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.handler.exception.RedirectException;
import com.example.demo.handler.exception.UnAuthorizedException;

/**
 * View 렌더링을 위해 ModelView 객체를 반환 하도록 설정되어 있음 예외처리 Page를 Return 할 때 사용
 * 
 * @ResponseBody 를 붙이면 Data를 내려 줄 수 있음
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	
	/**
	 * 모든 예외 클래스를 알 수 없기 때문에 로깅으로 확인 할 수 있도록 설정
	 * 참고 동기적 방식에 로깅은 지양(@slf4j 권장) 
	 */
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("-----------------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("-----------------------");
	}

	/**
	 * @return 에러 페이지로 이동 처리
	 */
	@ExceptionHandler(RedirectException.class)
	public ModelAndView redirectExceptionException(RedirectException e) {
		ModelAndView modelAndView = new ModelAndView("errorPage");
		modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		modelAndView.addObject("message", e.getMessage());
		return modelAndView; // 페이지 반환 + 데이터 내려줌
	}
	
	/**
	 * Data 로 예외를 내려 주기 위해  
	 * ResponseBody 활용 
	 * 브라우저에서 스크립트 코드로 동작 
	 */
	@ResponseBody
	@ExceptionHandler(DataDeliveryException.class)
	public String dataDeliveryExceptionException(DataDeliveryException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("window.history.back();");
		sb.append("</script>");
		return sb.toString();
	}
	
	@ResponseBody
	@ExceptionHandler(UnAuthorizedException.class)
	public String unAuthorizedException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "');");
		// Todo - 로그인 페이지로 수정 예정
		sb.append("location.href='/user/sign-in';");
		sb.append("</script>");
		return sb.toString();
	}
}
