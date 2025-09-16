package com.db.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TradeStoreJobScheduler {

	@Autowired
    private JobLauncher jobLauncher;
	
	@Autowired
    private Job myBatchJob;

    // Run every 1 minute
    @Scheduled(fixedRate = 60000)
    public void runJob() throws Exception {
        jobLauncher.run(myBatchJob, new JobParameters());
    }
}
