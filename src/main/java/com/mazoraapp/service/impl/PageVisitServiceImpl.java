package com.mazoraapp.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazoraapp.model.PageVisit;
import com.mazoraapp.repository.PageVisitRepository;
import com.mazoraapp.service.OrderService;
import com.mazoraapp.service.PageVisitService;

@Service
public class PageVisitServiceImpl implements PageVisitService {

    @Autowired
    private PageVisitRepository pageVisitRepository;

    public Long incrementVisit(String pageName) {
        PageVisit visit = pageVisitRepository.findByPageName(pageName)
                .orElseGet(() -> {
                    PageVisit newVisit = new PageVisit();
                    newVisit.setPageName(pageName);
                    newVisit.setTotalVisitCount(0L);
                    
                    return newVisit;
                });

        visit.setTotalVisitCount(visit.getTotalVisitCount() + 1);
        pageVisitRepository.save(visit);
        return visit.getTotalVisitCount();
    }

    public Long getVisitCount(String pageName) {
        return pageVisitRepository.findByPageName(pageName)
                .map(PageVisit::getTotalVisitCount)
                .orElse(0L);
    }
}
