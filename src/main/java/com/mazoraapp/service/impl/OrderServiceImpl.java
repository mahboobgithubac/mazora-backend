package com.mazoraapp.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mazoraapp.dto.OrderDTO;
import com.mazoraapp.dto.OrderItemDTO;
import com.mazoraapp.dto.OrderItemRequest;
import com.mazoraapp.dto.OrderRequest;
import com.mazoraapp.exception.ProductNotFoundException;
import com.mazoraapp.exception.UsernameNotFoundException;
import com.mazoraapp.model.Order;
import com.mazoraapp.model.OrderItem;
import com.mazoraapp.model.Product;
import com.mazoraapp.model.User;
import com.mazoraapp.repository.OrderRepository;
import com.mazoraapp.repository.ProductRepository;
import com.mazoraapp.repository.UserRepository;
import com.mazoraapp.service.OrderService;
import jakarta.transaction.Transactional;
@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
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
		return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private OrderDTO convertToDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
	    dto.setEmail(order.getUser().getEmail());
		
		dto.setShippingAddress(order.getShippingAddress());
		dto.setOrderDate(order.getOrderDate());
		dto.setUserName(order.getUser().getName());
		
	
		double totalAmount = order.getItems()
		        .stream()
		        .mapToDouble(item -> item.getPrice() * item.getQuantity())
		        .sum();
	    dto.setTotalAmount(totalAmount);
//		
		
		
		
		
		List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item -> {
			OrderItemDTO itemDTO = new OrderItemDTO();
			itemDTO.setProductTitle(item.getProduct().getTitle());
			itemDTO.setPrice(item.getPrice());
			itemDTO.setQuantity(item.getQuantity());
			itemDTO.setImage(item.getProduct().getImageUrl()); // ✅ Set image here
		  	return itemDTO;
		}).collect(Collectors.toList());
		dto.setItems(itemDTOs);
		return dto;
	}
	public List<OrderDTO> getAllOrders() {
	    List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));
	    return orders.stream()
	                 .map(this::convertToDTO)
	                 .collect(Collectors.toList());
	}
}