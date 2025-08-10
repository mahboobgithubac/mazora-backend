package com.mazoraapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mazoraapp.interceptor.RequestLoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	  @Autowired
	    private RequestLoggingInterceptor requestLoggingInterceptor;
	//for railway file upload
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/uploads/**")
	                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/")
	                .setCacheControl(CacheControl.noStore());
	        System.out.println("\"file:\" + System.getProperty(\"user.dir\") + \"/uploads/\"->"+"file:" + System.getProperty("user.dir") + "/uploads/");
	    }
	  


	  @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(requestLoggingInterceptor)
	                .addPathPatterns("/**")
	                .excludePathPatterns(
	                        "/uploads/**",     // Exclude uploaded images
	                        "/images/**",      // Exclude static image folder if used
	                        "/css/**",         // Exclude CSS files
	                        "/js/**",          // Exclude JavaScript files
	                        "/favicon.ico",    // Exclude favicon
	                        "/webjars/**",     // Exclude Spring Boot static resources
	                        "/**/*.png",       // Exclude all PNG files
	                        "/**/*.jpg",       // Exclude all JPG files
	                        "/**/*.jpeg",
	                        "/**/*.gif",
	                        "/**/*.svg"
	                );
	    }
	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	            .allowedOrigins(
	                "https://mazora.netlify.app",
	                "http://localhost:3000"
	            )
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	            .allowedHeaders("*")
	            .allowCredentials(true);
	    }
	}
