package com.ax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ax.model.TickModel;
import com.ax.repository.TickerRepository;
import com.ax.service.orquestrator.FileOrquestrator;

@RestController
public class TickerController {

	@Autowired
	TickerRepository repository;

	@Autowired
	FileOrquestrator orquestrator;

	@RequestMapping("/sync")
	public String Sync() {
		orquestrator.SyncFiles();
		return "sync ok!";
	}

	@RequestMapping("/load")
	public String process() {
		return "Done";
	}

	@RequestMapping("/find")
	public List<TickModel> findById(@RequestParam("symbol") String symbol) {
		return null;
	}

}