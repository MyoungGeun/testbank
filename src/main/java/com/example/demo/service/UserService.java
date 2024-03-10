package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.SigninDTO;
import com.example.demo.handler.exception.DataDeliveryException;
import com.example.demo.handler.exception.RedirectException;
import com.example.demo.repository.interfaces.UserRepository;
import com.example.demo.repository.model.User;
import com.example.demo.utils.Define;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;	
	// 1. 코드 추가 
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	/**
	  * @Method Name : createUser
	  * @작성일 : 2024. 3. 10.
	  * @작성자 : 장명근
	  * @변경이력 : 
	  * @Method 설명 : 회원가입 서비스 처리
	  */
	@Transactional
	public void createUser(SignUpDTO dto) {
		
		int result = 0; 
		  
		if (!dto.getMFile().isEmpty()) {
			String[] fileNames = uploadFile(dto.getMFile());
			// dto 객체 상태 변경 
			dto.setOriginFileName(fileNames[0]);
			dto.setUploadFileName(fileNames[1]);
	    }
		
		try {
			// 회원가입 요청자기 제출한 password 부분을 암화호 처리
			String hashPwd = passwordEncoder.encode(dto.getPassword());
			dto.setPassword(hashPwd);
			
			result = userRepository.insert(dto.toUser());
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.INVALID_INPUT,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if (result != 1) {
			throw new DataDeliveryException(Define.FAIL_TO_CREATE_USER, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 
	 * @param file
	 * MultipartFile getOriginalFilename : 사용자가 작성한 파일 명 
	 * uploadFileName : 서버 컴퓨터에 저장 될 파일 명 
	 * @return index 0, 1 
	 */
	private String[] uploadFile(MultipartFile file) {
	    if (file.getSize() > Define.MAX_FILE_SIZE) {
	        throw new DataDeliveryException("파일 크기는 20MB 이상 클 수 없습니다", HttpStatus.BAD_REQUEST);
	    }
	    	
	    // 서버 컴퓨터에 파일 넣을 디렉토리가 있는지 검사 
	    // 코드 수정 
	    // getAbsolutePath() : 파일 시스템의 절대 경로를 나타냅니다
	    // (리눅스 또는 MacOS)
	    String saveDirectory = new File(uploadDir).getAbsolutePath();
	    File directory = new File(saveDirectory);
	    // 폴더가 없다면 생성 처리 
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }
	    
	    // 파일 이름 (중복 처리 예방) 
	    String uploadFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	    String uploadPath = saveDirectory + File.separator + uploadFileName;
	    File destination = new File(uploadPath);

	    try {
	        file.transferTo(destination);
	    } catch (IllegalStateException | IOException e) {
	        throw new DataDeliveryException("파일 업로드 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    return new String[] {file.getOriginalFilename(), uploadFileName};
	}
	
	
	// 코드 추가 부분 
	// 사용자 이름만으로 정보 조회
	public User readUserByUsername(String username) {
		return userRepository.findByUsername(username);
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
			userEntity = userRepository.findByUsername(dto.getUsername());
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
