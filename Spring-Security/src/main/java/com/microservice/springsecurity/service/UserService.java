package com.microservice.springsecurity.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.springsecurity.model.Users;
import com.microservice.springsecurity.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // Find the user by username in the user repository
	    Users foundedUser = userRepository.findByUsername(username);
	    
	    // If user is not found, return null
	    if (foundedUser == null) {
	        return null;
	    }
	    
	    // Extract the username and password from the found user
	    String user = foundedUser.getUsername();
	    String pass = foundedUser.getPassword();
	    
	    // Create and return a UserDetails object representing the user
	    return new User(user, pass, new ArrayList<>());
	}


}
