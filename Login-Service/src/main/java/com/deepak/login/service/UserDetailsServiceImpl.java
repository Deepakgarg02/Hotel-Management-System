package com.deepak.login.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepak.login.jwtsecurity.AuthEntryPointJwtImpl;
import com.deepak.login.model.Users;
import com.deepak.login.repository.UserRepo;
 

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    // This method is responsible for loading a user's details based on the provided username
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user in the repository by their username
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        
        logger.info("User Details :- {}", user);

        // Construct and return a UserDetailsImpl object representing the user's details
        logger.info("User Details Impl:- {}", UserDetailsImpl.build(user));
        return UserDetailsImpl.build(user);
 
    }
}

//Overall, the UserDetailsServiceImpl class acts as a bridge between the user repository and Spring Security's 
//authentication mechanism. It loads the user's details from the repository based on the 
//provided username and constructs a UserDetails object for authentication purposes.


//The @Transactional annotation ensures that the method is executed within a transaction. This 
//is useful when interacting with the database, as it guarantees data consistency.