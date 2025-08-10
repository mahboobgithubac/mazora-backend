package com.mazoraapp.service;
import java.util.List;
import com.mazoraapp.dto.OrderDTO;
import com.mazoraapp.dto.OrderRequest;
import com.mazoraapp.exception.ProductNotFoundException;
import com.mazoraapp.exception.UsernameNotFoundException;
import com.mazoraapp.model.Order;
public interface OrderService {
	OrderDTO placeOrder( OrderRequest orderRequest) throws UsernameNotFoundException, ProductNotFoundException;
    List<Order> getOrdersByEmail(String email);
    List<OrderDTO> getOrdersByUserId(Long userId);
	List<OrderDTO> getAllOrders();
}
