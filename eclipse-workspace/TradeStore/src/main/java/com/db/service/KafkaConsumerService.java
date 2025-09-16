package com.db.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.db.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {
	
	private TradeService tradeService;
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public KafkaConsumerService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	 @KafkaListener(topics = "tradestore-topic", groupId = "tradestore-group")
	 public void consume(String message) {
		 
	     System.out.println("Message consumed: " + message);
	     
	     Trade trade;
		 try {
			trade = objectMapper.readValue(message, Trade.class);
			tradeService.saveTrade(trade);
		 } 
		 catch (JsonMappingException e) {
		    System.out.println("Consumer Service - Failed to parse during json mapping: " + message);
		 } 
		 catch (JsonProcessingException e) {
		 	System.out.println("Consumer Service - Failed to parse during json processing: " + message);
		 }	     
	 }
}
