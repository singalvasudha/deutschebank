package com.db.service;

import com.db.model.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    private ObjectMapper objectMapper;
    private Trade sampleTrade;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        sampleTrade = new Trade();
        sampleTrade.setTradeId("T123");
        sampleTrade.setVersion(1);
        sampleTrade.setCounterPartyId("CP001");
        sampleTrade.setBookId("B001");
        sampleTrade.setMaturityDate("15/09/2025");
        sampleTrade.setCreatedDate("10/09/2025");
        sampleTrade.setExpired(false);
    }

    @Test
    void testConsume_ValidMessage() throws Exception {
        // Convert trade object to JSON
        String jsonMessage = objectMapper.writeValueAsString(sampleTrade);

        // Call the consume method
        kafkaConsumerService.consume(jsonMessage);

        // Verify that tradeService.saveTrade() was called with the correct trade
        verify(tradeService, times(1)).saveTrade(Mockito.refEq(sampleTrade));
    }

    @Test
    void testConsume_InvalidJson() {
        String invalidJson = "{ invalid json }";

        // Call the consume method with invalid JSON
        kafkaConsumerService.consume(invalidJson);

        // Verify that tradeService.saveTrade() was never called
        verify(tradeService, never()).saveTrade(any());
    }

    @Test
    void testConsume_EmptyMessage() {
        String emptyMessage = "";

        // Call the consume method with empty string
        kafkaConsumerService.consume(emptyMessage);

        // Verify that tradeService.saveTrade() was never called
        verify(tradeService, never()).saveTrade(any());
    }
}
