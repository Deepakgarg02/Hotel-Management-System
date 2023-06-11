package com.microservice.springsecurity.filter;

import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microservice.springsecurity.service.UserService;
import com.microservice.springsecurity.utils.JwtUtils;

@Service
public class JwtFilterRequest extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtil;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extract the "Authorization" header from the request
		String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;

		// Check if the header is present and has the correct format ("Bearer ")
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// Extract the JWT token by removing the "Bearer " prefix
			jwtToken = authorizationHeader.substring(7);
			// Extract the username from the JWT token using the JwtUtils class
			username = jwtUtil.extractUsername(jwtToken);
		}

		// Check if the username is not null and there is no existing authentication in
		// the security context
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Retrieve the user details from the UserService based on the username
			UserDetails currentUserDetails = userService.loadUserByUsername(username);

			// Validate the JWT token by comparing it against the user details using the
			// JwtUtils class
			Boolean token = jwtUtil.validateToken(jwtToken, currentUserDetails);

			// If the token is valid, create a UsernamePasswordAuthenticationToken with the
			// user details
			if (token) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						currentUserDetails, null, currentUserDetails.getAuthorities());

				// Set additional details of the authentication request using
				// WebAuthenticationDetailsSource
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the created authentication token in the security context
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		// Continue processing the request by invoking the filter chain
		filterChain.doFilter(request, response);
	}
}
