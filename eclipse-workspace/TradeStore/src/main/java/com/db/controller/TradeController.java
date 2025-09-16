package com.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.model.Trade;
import com.db.service.TradeService;

@RestController
@RequestMapping("/tradeStore")
public class TradeController {
	
	@Autowired
	TradeService tradeService;
	
	@PostMapping(value = "/saveTrade")
	public void saveTrade(@RequestBody Trade trade) {
		tradeService.saveTrade(trade);		
	}
	
	@GetMapping(value = "/getTrade")
	public Trade getTrade(@RequestBody String tradeId) {
		Trade trade = tradeService.getTrade(tradeId);	
		return trade;
	}
	
}
