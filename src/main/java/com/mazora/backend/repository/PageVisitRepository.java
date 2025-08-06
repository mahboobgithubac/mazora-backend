package com.mazora.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mazora.backend.model.PageVisit;

import java.util.Optional;

public interface PageVisitRepository extends JpaRepository<PageVisit, Long> {
    Optional<PageVisit> findByPageName(String pageName);
}