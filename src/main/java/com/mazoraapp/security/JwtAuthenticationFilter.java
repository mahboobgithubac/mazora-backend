package com.mazoraapp.security;

//import com.mazoraapp.service.JwtService;

import com.mazoraapp.service.UserService;
import com.mazoraapp.config.JwtService;
import com.mazoraapp.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;

	// Extract JWT from Authorization Header
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Remove "Bearer " prefix
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		//System.out.println("At 47  path= " + path);
		// ✅ Skip JWT check for public endpoints
		if (path.startsWith("/api/auth/")) {
		//	System.out.println("inside   if (path.startsWith(\"/api/auth/\")) { ");
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String jwt = getJwtFromRequest(request);
			if (jwt != null && jwtService.validateToken(jwt)) {
				String email = jwtService.extractUsername(jwt);
				if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		} catch (Exception ex) {
			System.err.println("Cannot set user authentication: " + ex.getMessage());
			ex.printStackTrace();
		}

		filterChain.doFilter(request, response);
	}

}