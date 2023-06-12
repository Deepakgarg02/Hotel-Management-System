package com.deepak.login.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deepak.login.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String username;
	
	private String email;

//	The @JsonIgnore annotation on the password field indicates that the field should be ignored during serialization and deserialization,
//	providing additional security by not exposing the password in JSON responses.
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	// Build a UserDetailsImpl object from a User object
	public static UserDetailsImpl build(Users user) {
		// Map the roles of the user to GrantedAuthority objects
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}


	// Check if the user's account is non-expired (always returns true in this
	// implementation)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// Check if the user's account is non-locked (always returns true in this
	// implementation)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Check if the user's credentials are non-expired (always returns true in this
	// implementation)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Check if the user's account is enabled (always returns true in this
	// implementation)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// Compare UserDetailsImpl objects based on their IDs
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
