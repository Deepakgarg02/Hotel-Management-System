package com.microservice.springsecurity.utils;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {

//	The JwtUtils class encapsulates the JWT-related functionality,
//	making it easier to handle JWT operations without duplicating code.

//	The class provides methods to extract specific claims from a JWT token, 
//	such as the username and expiration date.

//	It provides a convenient way to generate and validate JWT tokens 
//	for authentication and authorization purposes.

	private final String SECRET_KEY = "secret232422rds43dwadq23424dfsdfsdrfw353w45rw3432sedrfw3423234rw3af";
	private final long TOKEN_VALIDITY_MS = 1000 * 60 * 60 * 10; // 10 hours

	// Extract the username from the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);

		// Functality of Line no 27
//	        return extractClaim(token, new Function<Claims, String>() {
//	            @Override
//	            public String apply(Claims claims) {
//	                return claims.getSubject();
//	            }
	}

	// Extract the expiration date from the token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Extract a specific claim from the token's claims
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Parse the token and extract all claims
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	// Check if the token has expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Generate a new token for the provided UserDetails
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	// Create a new token with the provided claims and subject (username)
	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + TOKEN_VALIDITY_MS);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	// Validate the token against the provided UserDetails
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
