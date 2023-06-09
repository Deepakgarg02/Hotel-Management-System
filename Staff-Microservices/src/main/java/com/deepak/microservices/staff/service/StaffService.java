package com.deepak.microservices.staff.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.staff.exception.StaffNotFoundException;
import com.deepak.microservices.staff.model.Staff;

@Service
public interface StaffService {

	List<Staff> getAllStaffs();

	Optional<Staff> getStaffById(String staffId) throws StaffNotFoundException;

	void modifyStaffById(String staffId, Staff staff) throws StaffNotFoundException;

	void deleteStaffById(String staffId) throws StaffNotFoundException;

	void addStaff(Staff staff);

	public List<Staff> getStaffByName(String staffName) throws StaffNotFoundException;

}
