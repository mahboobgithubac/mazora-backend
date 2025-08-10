package com.mazoraapp.controller;
import org.springframework.web.bind.annotation.*;

import com.mazoraapp.service.PageVisitService;
@RestController
@RequestMapping("/api/visits")
public class PageVisitController {
    private final PageVisitService service;
    public PageVisitController(PageVisitService service) {
        this.service = service;
    }
    @PostMapping("/{pageName}")
    public Long incrementVisit(@PathVariable String pageName) {
    	System.out.println(" public Long incrementVisit(@PathVariable String pageName) {");
        return service.incrementVisit(pageName);
    }
    @GetMapping("/{pageName}")
    public Long getVisitCount(@PathVariable String pageName) {
        return service.getVisitCount(pageName);
    }
}
