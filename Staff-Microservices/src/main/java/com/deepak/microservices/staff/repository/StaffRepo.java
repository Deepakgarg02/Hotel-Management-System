package com.deepak.microservices.staff.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.microservices.staff.model.Staff;

@Repository
public interface StaffRepo extends MongoRepository<Staff, String>{

	List<Staff> findByStaffName(String staffName);

}
