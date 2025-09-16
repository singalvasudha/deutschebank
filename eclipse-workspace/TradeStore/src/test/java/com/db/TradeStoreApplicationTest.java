package com.db;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeStoreApplicationTest {

	@Test
    void contextLoads() {
        // Verifies that Spring context starts without errors
		assertTrue(true, "TradeStoreApplication should load successfully.");
    }
	
}
