package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.handler.AuthInterceptor;

import lombok.RequiredArgsConstructor;

//@Configuration --> 스프링 부트 설정 클래스이다.
//내부에 메서드를 동작을 통한 Bean 객체 생성시 사용
@Configuration // IoC 대상  
@RequiredArgsConstructor // final 시 사용 
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private final AuthInterceptor authInterceptor;
	// 코드 추가 
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**")
				.addPathPatterns("/auth/**");
	}
	
	
	
	
	// 코드 추가 부분  
	// SpringSecurityCrypto 모듈에서 제공하는 BCryptPasswordEncoder 객체를
	// 어디에서든지 사용할 수 있도록 IoC 처리 합니다.
	@Bean // IoC 대상 - 싱글톤 처리
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 코드 추가 
	// 프로제트에서 사용할 가상 경로 정의 - /images/uploads/ 
	// 실제 서버 컴퓨터에 위치한 경로 정의 
	// - file:///C:\\spring_upload\\bank\\upload/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("images/uploads/**")
		.addResourceLocations("file:" + uploadDir);
	}
	
}
