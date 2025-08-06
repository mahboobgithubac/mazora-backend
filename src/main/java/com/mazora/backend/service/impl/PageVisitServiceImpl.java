package com.mazora.backend.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazora.backend.model.PageVisit;
import com.mazora.backend.repository.PageVisitRepository;
import com.mazora.backend.service.OrderService;
import com.mazora.backend.service.PageVisitService;

@Service
public class PageVisitServiceImpl implements PageVisitService {

    @Autowired
    private PageVisitRepository pageVisitRepository;

    public Long incrementVisit(String pageName) {
        PageVisit visit = pageVisitRepository.findByPageName(pageName)
                .orElseGet(() -> {
                    PageVisit newVisit = new PageVisit();
                    newVisit.setPageName(pageName);
                    newVisit.setVisitCount(0L);
                    return newVisit;
                });

        visit.setVisitCount(visit.getVisitCount() + 1);
        pageVisitRepository.save(visit);
        return visit.getVisitCount();
    }

    public Long getVisitCount(String pageName) {
        return pageVisitRepository.findByPageName(pageName)
                .map(PageVisit::getVisitCount)
                .orElse(0L);
    }
}
