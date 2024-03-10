package com.example.demo.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.HistoryAccountDTO;
import com.example.demo.repository.model.History;

@Mapper
public interface HistroyRepository {
	
	public int insert(History history);
	public int updateById(History history);
	public int deleteById(Integer id);
	
	// 계좌 조회 
	public History findById(Integer id);
	public List<History> findAll();
	
	// 코드 추가 부분
	public List<HistoryAccountDTO> findByAccountIdAndTypeOfHistory(@Param("type") String type,
			@Param("accountId") Integer accountId);
}
