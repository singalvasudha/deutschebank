package com.db.service;

import com.db.model.Trade;
import com.db.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() throws Exception {
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        trade = new Trade();
        trade.setTradeId("T123");
        trade.setVersion(2);
        trade.setMaturityDate(sdf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))); // tomorrow
        trade.setCounterPartyId("CP001");
        trade.setBookId("B001");
        trade.setExpired(false);

        // Mock repository for latestTradeVersion
        Trade storedTrade = new Trade();
        storedTrade.setTradeId("T123");
        storedTrade.setVersion(1); // older version
        when(tradeRepository.findById(anyString())).thenReturn(Optional.of(storedTrade));
    }

    @Test
    void testSaveTrade_Success() {
        tradeService.saveTrade(trade);

        // Verify repository save called
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testSaveTrade_OldVersion_NotSaved() {
        trade.setVersion(0); // older than stored version

        tradeService.saveTrade(trade);

        // Should not save
        verify(tradeRepository, never()).save(trade);
    }

    @Test
    void testSaveTrade_ExpiredMaturityDate_NotSaved() throws Exception {
        // Set maturity date to yesterday
        Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        trade.setMaturityDate(sdf.format(yesterday));

        tradeService.saveTrade(trade);

        // Should not save
        verify(tradeRepository, never()).save(trade);
    }

    @Test
    void testGetTrade_Found() {
        Trade result = tradeService.getTrade("T123");

        assertEquals("T123", result.getTradeId());
        assertEquals(1, result.getVersion());
    }

    @Test
    void testGetTrade_NotFound() {
        Trade result = tradeService.getTrade("T999");

        assertNotNull(result);
        assertNotNull(result.getTradeId());
        assertEquals(1, result.getVersion());
    }
}
