package com.mazora.backend.repository;

import com.mazora.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}