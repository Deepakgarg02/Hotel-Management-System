package com.deepak.login.jwtsecurity;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deepak.login.service.UserDetailsServiceImpl;

//It is responsible for filtering incoming requests and processing the JWT token for authentication.
//This will run when you will Signin the app
@Order
public class AuthRequestTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthRequestTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Parse the JWT token from the request header
			logger.debug("=============== filter =================");
			logger.info("Request: {}", request);
			String jwt = parseJwt(request);
			logger.info("Extracted JWT token: {}", jwt);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				// Extract the username from the JWT token
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				logger.info("Username: {}", username);

				// Load the user details from the userDetailsService based on the username
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// Create an authentication token using the user details and set it in the
				// SecurityContextHolder
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				logger.info("UserName Passwor Validation token : {}", authentication);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				logger.debug("=============== invalid token =================");
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Not Getting");
			return;
		}

		// Continue the filter chain
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		// Retrieve the Authorization header from the request
		logger.info("Request Header: {}", request.getHeader("Authorization"));
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			logger.debug("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
		}

		String headerAuth = request.getHeader("Authorization");

		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			// Extract the JWT token from the Authorization header
			return headerAuth.substring(7);
		}
		logger.info("Authorization header value: {}", headerAuth);
		return null;
	}

}

//Overall, the AuthTokenFilter class acts as a filter in the Spring Security chain, 
//responsible for validating and processing JWT tokens. It extracts the token from the
//request, validates it, retrieves user details, and sets the authentication token in the
//SecurityContextHolder for further authentication and authorization checks.
