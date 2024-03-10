package com.example.demo.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.repository.model.User;

@Mapper
public interface UserRepository {
	
	public int insert(User user);
	public int updateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id);
	public List<User> findAll();

	// 코드 추가 1단계 - 조회 : username, password
	// 주의!! - 파라미터가 2개 이상일 경우 @Param 어노테이션을 반드시 선언
	public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
	public User findByUsername(String username);

}
