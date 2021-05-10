package com.uvik.studentdatamanagement.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@PropertySource(value = "file:${user.home}/application.properties")
public class WebMvcConfiguration extends WebMvcConfigurationSupport
{ 
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
		{
	        return new PropertySourcesPlaceholderConfigurer();
	    }
}