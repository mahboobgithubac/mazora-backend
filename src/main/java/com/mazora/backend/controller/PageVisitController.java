package com.mazora.backend.controller;
import org.springframework.web.bind.annotation.*;

import com.mazora.backend.service.PageVisitService;

@RestController
@RequestMapping("/api/visits")
//@CrossOrigin(origins = "*") // or restrict to React/Netlify domain
@CrossOrigin(origins = {"http://localhost:3000", "https://your-app.netlify.app"})
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
