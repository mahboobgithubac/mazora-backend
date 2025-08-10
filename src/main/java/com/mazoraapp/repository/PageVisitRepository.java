package com.mazoraapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.mazoraapp.model.PageVisit;

import java.util.Optional;
public interface PageVisitRepository extends JpaRepository<PageVisit, Long> {
    Optional<PageVisit> findByPageName(String pageName);
}