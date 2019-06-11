package com.ax.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ax.model.TickModel;

public interface TickerRepository extends CrudRepository<TickModel, Long>{
	List<TickModel> findBySimbolo(String simbolo);
	List<TickModel> findBySimboloAndQuantidade(String simbolo, Long quantidade);
	
}
