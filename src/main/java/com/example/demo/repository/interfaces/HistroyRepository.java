package com.example.demo.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.repository.model.History;

@Mapper
public interface HistroyRepository {
	
	public int insert(History history);
	public int updateById(History history);
	public int deleteById(Integer id);
	
	// 계좌 조회 
	public History findById(Integer id);
	public List<History> findAll();
}