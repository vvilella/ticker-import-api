package com.ax.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ax.model.TickModel;
import com.ax.repository.TickerRepository;
import com.ax.service.CsvService;

@RestController
public class TickerImportController {
	@Autowired
	CsvService processor;
	@Autowired
	TickerRepository repository;
	
	@RequestMapping("/sync")
	public void Sync() {
		
	}
	
	

	@RequestMapping("/load")
	public String process() {

		try {
			// CsvProcessor processor = new CsvProcessor();
			processor.ProcessFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Done";
	}

	@RequestMapping("/find")
	public Iterable<TickModel> findAll() {

		return repository.findAll();
	}

	@RequestMapping("/find/{symbol}")
	public List<TickModel> findById(@RequestParam("symbol") String symbol) {
		return repository.findBySimbolo(symbol);
	}


}