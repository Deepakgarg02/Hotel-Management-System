package com.deepak.microservices.staff.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deepak.microservices.staff.model.Staff;

@Service
public interface StaffService {

	List<Staff> getAllStaffs();

	Optional<Staff> getStaffById(long staffId);

	void modifyStaffById(long staffId, Staff staff);

	void deleteStaffById(long staffId);

	void addStaff(Staff staff);

	public List<Staff> getStaffByName(String staffName);

}
