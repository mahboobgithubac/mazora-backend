package com.mazoraapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mazoraapp.dto.OrderDTO;
import com.mazoraapp.dto.OrderRequest;
import com.mazoraapp.exception.ProductNotFoundException;
import com.mazoraapp.exception.UsernameNotFoundException;
import com.mazoraapp.model.Order;
import com.mazoraapp.model.User;
import com.mazoraapp.repository.UserRepository;
import com.mazoraapp.service.OrderService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = {"http://localhost:3000", "https://your-app.netlify.app"})


//@CrossOrigin(origins = "https://mazora.netlify.app/")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
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
		 logger.info("order placing by user for product: {}", orderRequest.getUserId()+","+orderRequest.getItems());
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
		logger.info("Received request to fetch orders for userId: {}", userId);
		return orderService.getOrdersByUserId(userId);
	}
	@PreAuthorize("hasRole('ADMIN')") // Optional: restrict to admin
	@GetMapping("/admin/orders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
	    List<OrderDTO> orders = orderService.getAllOrders();
	    return ResponseEntity.ok(orders);
	}
}
