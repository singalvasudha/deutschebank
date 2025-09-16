package com.db.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.service.KafkaProducerService;

@RestController
@RequestMapping("/kafkastreams")
public class KafkaProducerController {
	
	private KafkaProducerService kafkaProducerService;
	
	public KafkaProducerController(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;		
	}
	
	@GetMapping("/send")
	public String sendMessage(@RequestBody String message) {
		kafkaProducerService.sendMessage(message);
		return "Message sent from Producer Controller: " + message;
	}

}
