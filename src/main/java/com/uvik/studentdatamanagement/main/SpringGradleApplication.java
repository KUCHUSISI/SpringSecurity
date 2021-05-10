package com.uvik.studentdatamanagement.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.uvik.sdm.service.StudentService;


@CrossOrigin
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.uvik")
@EnableMongoRepositories("com.uvik.repository")
@EnableWebSecurity
@EnableMongoAuditing
@Configuration
public class SpringGradleApplication
{
	
	public static void main(String[] args) 
	{
		Logger logger=LoggerFactory.getLogger(StudentService.class);
		logger.info("JustStarted the Application");
		SpringApplication.run(SpringGradleApplication.class, args);
		logger.info("the application has been ended");
	}
}