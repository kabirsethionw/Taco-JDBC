package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@ComponentScan("com.example.demo.web") used to tell spring to explicitly scan a directory
public class TacoCloudApplication /*implements WebMvcConfigurer*/ {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	} 
// 	Alternative of using WebConfig.java
//	@Override
//	 public void addViewControllers(ViewControllerRegistry registry) {  
//		 registry.addViewController("/").setViewName("home");
//	}
	
}
