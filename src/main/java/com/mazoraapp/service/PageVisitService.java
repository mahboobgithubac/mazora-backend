package com.mazoraapp.service;
public interface PageVisitService {
	  public Long incrementVisit(String pageName);
	    public Long getVisitCount(String pageName); 
}
