package com.mazora.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mazora.backend.dto.OrderDTO;
import com.mazora.backend.dto.OrderRequest;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.exception.UsernameNotFoundException;
import com.mazora.backend.model.Order;
import com.mazora.backend.model.User;
import com.mazora.backend.repository.UserRepository;
import com.mazora.backend.service.OrderService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

	private final OrderService orderService;
	@Autowired
	private UserRepository userRepository;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@Transactional
	public OrderDTO placeOrder(@Valid @RequestBody OrderRequest orderRequest)
			throws UsernameNotFoundException, ProductNotFoundException {
		User user = userRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		orderRequest.setUserId(user.getId()); // ✅ set user
		return orderService.placeOrder(orderRequest);
	}

	@GetMapping("/{email}")
	public List<Order> getOrders(@PathVariable String email) {
		return orderService.getOrdersByEmail(email);
	}

	@GetMapping("/user/{userId}")
	public List<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
		return orderService.getOrdersByUserId(userId);
	}
}
