package com.zainat;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zainat.service.ZainatService;

@SpringBootApplication
public class ZainatApplication /*implements CommandLineRunner*/ {

	@Autowired
	ZainatService zainatServiceImpl;

	public static void main(String[] args) {
//		SpringApplication.run(ZainatApplication.class, args);
		
		ClassPathXmlApplicationContext cpt = new ClassPathXmlApplicationContext("jobContext-operation.xml");
        cpt.start();
        JobLauncher jobLauncher = (JobLauncher) cpt.getBean("jobLauncher");
        Job job = (Job) cpt.getBean("operationExtraction");
        JobParameters parameter = new JobParametersBuilder().toJobParameters();
        try {
			jobLauncher.run(job, parameter);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

//	@Override
//	public void run(String... ags) throws ParseException {
//		zainatServiceImpl.process();
//	}
}
