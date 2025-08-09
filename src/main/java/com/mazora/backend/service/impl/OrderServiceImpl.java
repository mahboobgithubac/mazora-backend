package com.mazora.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mazora.backend.dto.OrderDTO;
import com.mazora.backend.dto.OrderItemDTO;
import com.mazora.backend.dto.OrderItemRequest;
import com.mazora.backend.dto.OrderRequest;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.exception.UsernameNotFoundException;
import com.mazora.backend.model.Order;
import com.mazora.backend.model.OrderItem;
import com.mazora.backend.model.Product;
import com.mazora.backend.model.User;
import com.mazora.backend.repository.OrderRepository;
import com.mazora.backend.repository.ProductRepository;
import com.mazora.backend.repository.UserRepository;
import com.mazora.backend.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	 String imagePathProd="https://mazora-backend-production.up.railway.app/uploads/";
//	 https://<your-railway-app>.up.railway.app/uploads/<filename>
	 String imagePathLocal="http://localhost:8089/uploads/";
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	@Transactional
	public OrderDTO placeOrder(OrderRequest orderRequest) throws UsernameNotFoundException, ProductNotFoundException {
		User user = userRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Order order = new Order();
		order.setShippingAddress(orderRequest.getShippingAddress());
		order.setUser(user);

		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderItemRequest itemReq : orderRequest.getItems()) {
			Product product = productRepository.findById(itemReq.getProductId())
					.orElseThrow(() -> new ProductNotFoundException("Product not found"));

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setOrder(order); // ✅ link Order
			orderItem.setQuantity(itemReq.getQuantity());
			orderItem.setPrice(product.getPrice());

			orderItems.add(orderItem);
		}

		order.setItems(orderItems); // if you have mappedBy relationship
		Order savedOrder = orderRepository.save(order);
		return convertToDTO(savedOrder);
	}

	@Override
	public List<Order> getOrdersByEmail(String email) {
		return orderRepository.findByUserEmail(email);
	}

	@Override
	public List<OrderDTO> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId); // ✅ now returns List<Order>
		// .orElseThrow(() -> new OrderNotFoundException("Order not found"));

		return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private OrderDTO convertToDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setShippingAddress(order.getShippingAddress());
		dto.setOrderDate(order.getOrderDate());
		dto.setUserName(order.getUser().getName());
		List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item -> {
			OrderItemDTO itemDTO = new OrderItemDTO();
			itemDTO.setProductTitle(item.getProduct().getTitle());
			itemDTO.setPrice(item.getPrice());
			itemDTO.setQuantity(item.getQuantity());
			itemDTO.setImage(item.getProduct().getImageUrl()); // ✅ Set image here
//		   itemDTO.setImage(imagePathProd+""+item.getProduct().getImageUrl()); // ✅ Set image here
		   itemDTO.setImage(item.getProduct().getImageUrl()); // ✅ Set image here
			
			
			//dto.setImage(imagePathProd+""+ product.getImageUrl());
		//	System.out.println("itemDTO.getImagee()->"+itemDTO.getImage());
			return itemDTO;
		}).collect(Collectors.toList());

		dto.setItems(itemDTOs);
		return dto;
	}
	public List<OrderDTO> getAllOrders() {
	    List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	    return orders.stream()
	                 .map(this::convertToDTO)
	                 .collect(Collectors.toList());
	}
}