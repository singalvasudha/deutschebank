package com.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.controller.KafkaProducerController;
import com.db.controller.TradeController;
import com.db.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class TradeStoreInvokeProducerTest {

	@Autowired
	TradeController tradeController;
	
	@Autowired
	KafkaProducerController kafkaProducerController;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testSendMessage() {
		Trade trade = populateTrade();
		String message = asJsonString(trade);
		kafkaProducerController.sendMessage(message);
	}
	
	String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}		
	}
	
	Trade populateTrade() {
		Trade trade = new Trade();
		
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setCounterPartyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate("20/11/2025");
		trade.setCreatedDate(formatter.format(new Date()));
		trade.setExpired(false);	
		
		return trade;		
	}
}
