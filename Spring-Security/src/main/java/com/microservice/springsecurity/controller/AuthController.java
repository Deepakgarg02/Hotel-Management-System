package com.microservice.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurity.model.*;
import com.microservice.springsecurity.repository.UserRepository;
import com.microservice.springsecurity.service.UserService;
import com.microservice.springsecurity.utils.JwtUtils;

@RestController
public class AuthController {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private AuthenticationManager authenticator;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/subs")
	private ResponseEntity<UserResponse> subscribeClient(@RequestBody UserRequest authReq) {
		// Create a new UserModel instance
		Users userModel = new Users();

		// Set the username and password from the request body
		userModel.setUsername(authReq.getUsername());
		userModel.setPassword(authReq.getPassword());

		try {
			// Save the UserModel in the user repository
			userrepo.save(userModel);
		} catch (Exception e) {
			// Return an error response if there is an exception during subscription
			return new ResponseEntity<UserResponse>(new UserResponse("Error during subscription"), HttpStatus.OK);
		}

		// Return a success response with a message indicating successful subscription
		return new ResponseEntity<UserResponse>(
				new UserResponse("Successful subscription for client " + authReq.getUsername()), HttpStatus.OK);
	}

	@PostMapping("/auth")
	private ResponseEntity<?> authenticateClient(@RequestBody UserRequest authReq) {
		// Extract the username and password from the request body
		String username = authReq.getUsername();
		String password = authReq.getPassword();

		try {
			// Perform authentication using the AuthenticationManager
			authenticator.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			// Return an error response if authentication fails
			return ResponseEntity.ok(new UserResponse("Invalid Credentials"));
		}

		// Load user details based on the provided username
		UserDetails userDetails = userService.loadUserByUsername(username);

		// Generate a JWT token using the JwtUtils
		String jwt = jwtUtils.generateToken(userDetails);

		// Return a success response with the JWT token
		return ResponseEntity.ok(new UserResponse(jwt));
	}

	@GetMapping("/test")
	private String testingToken() {
		try {
			// Return a success message if the endpoint is accessed and authenticated
			return "Testing Successful";
		} catch (Exception e) {
			// Return a prompt to login if the endpoint is accessed without authentication
			return "Please login first";
		}
	}

	@GetMapping("/dashboard")
	private String dashboard() {
		// Return a welcome message for the dashboard endpoint
		return "Welcome to the dashboard";
	}
}
