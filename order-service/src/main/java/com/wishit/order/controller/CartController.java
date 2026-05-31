package com.wishit.order.controller;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishit.order.dto.ResponseWrapper;
import com.wishit.order.entity.Cart;
import com.wishit.order.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService service;
	
	private static final Logger logger =
	        LoggerFactory.getLogger(CartController.class);
	
	@PostMapping("/add")
	public ResponseEntity<ResponseWrapper<Cart>> postItem( @RequestBody Cart cart) {
		logger.info("Received add cart request");
		ResponseWrapper<Cart> response = service.getCartByUserId(cart);
		
		
		if(response.getErrCode() != null) {
			logger.warn("Cart request failed with errorCode: {}", response.getErrCode());
			return ResponseEntity.badRequest().body(response);
		}
		logger.info("Cart request processed successfully");
		return ResponseEntity.ok(response);
	}
}
