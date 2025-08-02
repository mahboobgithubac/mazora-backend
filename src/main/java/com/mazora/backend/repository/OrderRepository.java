package com.mazora.backend.repository;

import com.mazora.backend.dto.OrderDTO;
import com.mazora.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmail(String email);

	List<OrderDTO> getOrdersByUserId(Long userId);

	List<Order> findByUserId(Long userId);
}
