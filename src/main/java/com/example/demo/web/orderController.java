package com.example.demo.web;

import com.example.demo.Order;
import com.example.demo.data.OrderRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.validation.Errors;

@Slf4j
@Controller
@RequestMapping("/order")
public class orderController {
	OrderRepository orderRepository;
	
	@Autowired
	public orderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	
	@GetMapping("/current")
	public String showDesignForm(Model model) {
		// TODO Auto-generated constructor stub
		model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if(errors.hasErrors()) { log.info(errors.getFieldError().toString()) ; return "orderForm";}
		log.info("Order submitted: "+order);
		orderRepository.save(order);
		sessionStatus.setComplete(); // Once the order is saved, you don’t need it hanging around in a session anymore. In fact, if you don’t clean it out, the order remains in session, including its associated tacos, and the next order will start with whatever tacos the old order contained.
		return "redirect:/";
	}

}
