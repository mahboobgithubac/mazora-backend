package com.mazoraapp.interceptor;
import com.mazoraapp.model.RequestLog;
import com.mazoraapp.repository.RequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Autowired
    private RequestLogRepository requestLogRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestLog log = new RequestLog();
        log.setIpAddress(request.getRemoteAddr());
        log.setEndpoint(request.getRequestURI());
        log.setMethod(request.getMethod());
        log.setReferer(request.getHeader("referer"));
        log.setUserAgent(request.getHeader("User-Agent"));
        requestLogRepository.save(log);
        return true;
    }
}