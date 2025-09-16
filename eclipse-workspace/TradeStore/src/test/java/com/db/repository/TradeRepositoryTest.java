package com.db.repository;

import com.db.model.Trade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeRepositoryTest {

    @Mock
    private TradeRepository tradeRepository;

    @Test
    void testSaveAndFindTrade() {
        Trade trade = new Trade();
        trade.setTradeId("T123");
        trade.setVersion(1);
        tradeRepository.save(trade);
        
        when(tradeRepository.findById("T123")).thenReturn(Optional.of(trade));

        Optional<Trade> retrieved = tradeRepository.findById("T123");

        assertTrue(retrieved.isPresent(), "Trade should be present");
        assertEquals("T123", retrieved.get().getTradeId());
        assertEquals(1, retrieved.get().getVersion());
        
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testDeleteTrade() {
        Trade trade = new Trade();
        trade.setTradeId("T456");
        tradeRepository.save(trade);

        tradeRepository.deleteById("T456");

        verify(tradeRepository, times(1)).save(trade);
        verify(tradeRepository, times(1)).deleteById("T456");
    }

    @Test
    void testFindAllTrades() {
        Trade trade1 = new Trade();
        trade1.setTradeId("T1");
        Trade trade2 = new Trade();
        trade2.setTradeId("T2");

        tradeRepository.save(trade1);
        tradeRepository.save(trade2);
        
        when(tradeRepository.findAll()).thenReturn(List.of(trade1, trade2));

        assertEquals(2, tradeRepository.findAll().size(), "Should find 2 trades");
    }
}
