package com.deepak.login.jwtsecurity;

import java.util.Date;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.deepak.login.service.UserDetailsImpl;

import io.jsonwebtoken.*;

//The JwtUtils class provides utility methods for working with JWT (JSON Web Token).
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${deepak.app.jwtSecret}")
	private String jwtSecret;

	@Value("${deepak.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	// Generate a JWT token for the authenticated user
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
	    Date now = new Date();
	    Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
//It takes an Authentication object as a parameter and retrieves the user's details from the
//principal. It constructs a JWT token using the Jwts.builder() and sets the subject (username)
//, issuedAt, expiration, and signs it with the specified algorithm and secret key. The token 
//is then compacted and returned.
		
		logger.info("UserPrincipal: {}", userPrincipal);

		String jwt = Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		
		logger.info("Jwt at Generate : {}", jwt);

		
		return jwt;
	}

	// Extract the username from the JWT token
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
			    .parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	// Validate the JWT token
	public boolean validateJwtToken(String authToken) {
		try {
			Jws<Claims> jwt = Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(authToken);
			logger.info("Jwt at Validate: {}", jwt);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
