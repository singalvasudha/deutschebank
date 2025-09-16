package com.db.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class TradeStoreJobCompletionNotificationListener implements JobExecutionListener {

	    @Override
	    public void beforeJob(JobExecution jobExecution) {
	        System.out.println("Job " + jobExecution.getJobInstance().getJobName() + " is starting.");
	    }

	    @Override
	    public void afterJob(JobExecution jobExecution) {
	        if (jobExecution.getStatus().equals(ExitStatus.COMPLETED)) {
	            System.out.println("Job " + jobExecution.getJobInstance().getJobName() + " completed successfully!");
	            // Perform success-specific actions
	        } else {
	            System.err.println("Job " + jobExecution.getJobInstance().getJobName() + " failed with status: " + jobExecution.getStatus());
	            // Perform failure-specific actions
	        }
	    }
}
