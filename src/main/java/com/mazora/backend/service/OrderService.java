package com.mazora.backend.service;

import java.util.List;

import com.mazora.backend.dto.OrderDTO;
import com.mazora.backend.dto.OrderRequest;
import com.mazora.backend.exception.ProductNotFoundException;
import com.mazora.backend.exception.UsernameNotFoundException;
import com.mazora.backend.model.Order;

public interface OrderService {
	OrderDTO placeOrder( OrderRequest orderRequest) throws UsernameNotFoundException, ProductNotFoundException;
    List<Order> getOrdersByEmail(String email);
    List<OrderDTO> getOrdersByUserId(Long userId);
}
