package com.db.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import com.db.model.Trade;
import jakarta.persistence.EntityManagerFactory;

@Configuration
//@EnableBatchProcessing
public class TradeStoreBatchConfig {

	private final EntityManagerFactory entityManagerFactory;

	public TradeStoreBatchConfig(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Bean
    public Job myBatchJob(JobRepository jobRepository, Step myStep, TradeStoreJobCompletionNotificationListener jobListener) {
		
		return new JobBuilder("myBatchJob", jobRepository)
                .start(myStep)
                .listener(jobListener)
                .build();
    }

	@Bean
	public JpaPagingItemReader<Trade> jpaItemReader() {
		JpaPagingItemReader<Trade> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("SELECT e FROM Trade e"); // Filter for expired items
		reader.setPageSize(100); // Adjust page size as needed
		reader.setTransacted(false);
		return reader;
	}

	@Bean
	public ItemProcessor<Trade, Trade> itemProcessor() {
		return trade -> {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	        Date maturityDate = simpleDateFormat.parse(trade.getMaturityDate());
	        Date today = new Date();
	        
	        if (maturityDate.before(today)) {
	            trade.setExpired(true);
	        }

			return trade;
		};
	}

	@Bean
	public JpaItemWriter<Trade> jpaItemWriter() {
		JpaItemWriter<Trade> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	 @Bean 
	 public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			 JpaPagingItemReader<Trade> jpaItemReader, ItemProcessor<Trade, Trade> itemProcessor,
             JpaItemWriter<Trade> jpaItemWriter) { 
		 
		 return new StepBuilder("step1", jobRepository)
	                .<Trade, Trade>chunk(10, transactionManager)
	                .reader(jpaItemReader)
	                .processor(itemProcessor) // Optional: if you need to transform the data
	                .writer(jpaItemWriter)
	                .allowStartIfComplete(true)
	                .build();
     }
	 
	 @Bean
	 public Job importUserJob(JobRepository jobRepository, Step step1, TradeStoreJobCompletionNotificationListener listener) {
	   return new JobBuilder("importUserJob", jobRepository)
	     .listener(listener)
	     .start(step1)
	     .build();
	 }
}
