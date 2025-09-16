package com.db.controller;

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

import com.db.service.KafkaProducerService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerControllerTest {
	
	 private MockMvc mockMvc;

	    @Mock
	    private KafkaProducerService kafkaProducerService;

	    @InjectMocks
	    private KafkaProducerController kafkaProducerController;

	    @BeforeEach
	    void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(kafkaProducerController).build();
	    }

	    @Test
	    void testSendMessage() throws Exception {
	        String message = "Hello Kafka";

	        Mockito.doNothing().when(kafkaProducerService).sendMessage(anyString());

	        mockMvc.perform(get("/kafkastreams/send")
	                .contentType(MediaType.TEXT_PLAIN)
	                .content(message))
	                .andExpect(status().isOk())
	                .andExpect(content().string("Message sent from Producer Controller: " + message));

	        Mockito.verify(kafkaProducerService).sendMessage(message);
	    }

}
