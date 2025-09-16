package com.db.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.db.model.Trade;
import com.db.service.TradeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {
	
	private MockMvc mockMvc;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private ObjectMapper objectMapper;

    private Trade sampleTrade;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
        objectMapper = new ObjectMapper();

        sampleTrade = new Trade();
        
        sampleTrade.setTradeId("T2");
        sampleTrade.setVersion(2);
        sampleTrade.setCounterPartyId("CP-2");
        sampleTrade.setBookId("B1");
        sampleTrade.setMaturityDate("20/06/2026");
        sampleTrade.setCreatedDate(formatter.format(new Date()));
        sampleTrade.setExpired(false);
    }
    
    @Test
    void testSaveTrade() throws Exception {
        Mockito.doNothing().when(tradeService).saveTrade(any(Trade.class));

        mockMvc.perform(post("/tradeStore/saveTrade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTrade)))
                .andExpect(status().isOk());

        Mockito.verify(tradeService).saveTrade(any(Trade.class));
    }

    @Test
    void testGetTrade() throws Exception {
        Mockito.when(tradeService.getTrade(anyString())).thenReturn(sampleTrade);

        mockMvc.perform(get("/tradeStore/getTrade")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"T3\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value("T2"))
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.maturityDate").value("20/06/2026"))
                .andExpect(jsonPath("$.expired").value(false));
    }

}
