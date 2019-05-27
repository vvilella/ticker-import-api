package com.ax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

	@Async
	@RequestMapping("/sync")
	public void Sync() {
		orquestrator.SyncFiles();
	}

	@Async
	@RequestMapping("/load")
	public String process() {
		return "Done";
	}

	@RequestMapping("/find")
	public List<TickModel> findById(@RequestParam("symbol") String symbol) {
		return null;
	}

}