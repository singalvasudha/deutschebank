package com.db.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    private final String TOPIC = "tradestore-topic";

    @BeforeEach
    void setUp() {
        // No special setup required
    }

    @Test
    void testSendMessage() {
        String message = "Hello Kafka";

        // Call the service method
        kafkaProducerService.sendMessage(message);

        // Verify that KafkaTemplate.send was called with correct topic and message
        verify(kafkaTemplate, times(1)).send(eq(TOPIC), eq(message));
    }
}
