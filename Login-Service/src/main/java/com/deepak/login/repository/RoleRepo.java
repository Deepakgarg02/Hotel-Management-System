package com.deepak.login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deepak.login.model.EnumRole;
import com.deepak.login.model.Roles;

@Repository
public interface RoleRepo extends MongoRepository<Roles, String> {

	Optional<Roles> findByName(EnumRole name);

}
