package com.deepak.login.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.login.jwtsecurity.JwtUtils;
import com.deepak.login.model.EnumRole;
import com.deepak.login.model.Roles;
import com.deepak.login.model.Users;
import com.deepak.login.payload.request.Login;
import com.deepak.login.payload.request.Signup;
import com.deepak.login.payload.response.JwtResponse;
import com.deepak.login.payload.response.MessageResponse;
import com.deepak.login.repository.RoleRepo;
import com.deepak.login.repository.UserRepo;
import com.deepak.login.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepository;

	@Autowired
	RoleRepo roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	// Handles the authentication request and generates a JWT token
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login loginRequest) {
		// Authenticates the user using the provided username and password
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Sets the authentication in the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generates a JWT token for the authenticated user
		String jwt = jwtUtils.generateJwtToken(authentication);
		logger.info(" JWT token: {}", jwt);

		// Retrieves user details from the authenticated principal
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		// Retrieves the roles of the user and collects them into a list of strings
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// Constructs the response containing the JWT token, user details, and roles
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	// Handles the user registration request
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody Signup signUpRequest) {
		// Checks if the username is already taken
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		// Checks if the email is already in use
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Creates a new user account using the provided information
		Users user = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Roles> roles = new HashSet<>();

		// If no roles are specified, assign the "RECEPTIONIST" role by default
		if (strRoles == null || strRoles.isEmpty()) {
			// If no roles are specified, assign the "RECEPTIONIST" role by default
			Roles receptionistRole = roleRepository.findByName(EnumRole.RECEPTIONIST)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(receptionistRole);
		} else {
			// Assign the specified roles to the user
			for (String role : strRoles) {
				EnumRole enumRole = EnumRole.valueOf(role.toUpperCase());
				Roles userRole = roleRepository.findByName(enumRole)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		}

		// Sets the roles for the user and saves the user to the repository
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
