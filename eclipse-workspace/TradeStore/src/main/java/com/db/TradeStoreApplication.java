package com.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.db.repository")
public class TradeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeStoreApplication.class, args);
	}

}
