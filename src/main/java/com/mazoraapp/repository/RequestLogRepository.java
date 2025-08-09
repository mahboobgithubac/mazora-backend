package com.mazoraapp.repository;
import com.mazoraapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}