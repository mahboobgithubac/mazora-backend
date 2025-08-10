package com.mazoraapp.model;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "page_visits")
@Data
public class PageVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pageName;
    private Long totalVisitCount;
}