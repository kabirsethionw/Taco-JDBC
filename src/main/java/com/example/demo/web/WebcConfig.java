package com.example.demo.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebcConfig  implements WebMvcConfigurer{ //WebMvcConfigurer provides methods for configuring spring MVC, its and interface and provides default implementation of methods but we can override them like we did with addViewControllers()
	@Override
	public void addViewControllers(ViewControllerRegistry registry) { //ViewControllerRegistry used to register one or more view controllers and addViewControllers on registry is called and passed in "/" it handles http get request, this method returns we ControllerRegistration object
		registry.addViewController("/").setViewName("home"); // we call  setViewName() on ControllerRegistration returned to specify the view returned is "home"
	}
}