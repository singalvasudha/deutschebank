package com.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	private static final String TOPIC = "tradestore-topic";

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {    	
    	kafkaTemplate.send(TOPIC, message);
    }

}
