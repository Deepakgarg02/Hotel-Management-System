package com.deepak.login.jwtsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwtImpl implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwtImpl.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Log the unauthorized error with the details of the authentication exception
		logger.error("Unauthorized error: {}", authException.getMessage());

		// Send an error response with HTTP status code 401 (Unauthorized) and an error
		// message
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}
}


//Overall, the AuthEntryPointJwt class serves as the entry point for handling unauthorized 
//requests in the JWT-based authentication mechanism. It logs the error and sends 
//an appropriate response to the client.