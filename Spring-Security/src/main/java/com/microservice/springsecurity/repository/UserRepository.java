package com.microservice.springsecurity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservice.springsecurity.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

	Users findByUsername(String username);

}
