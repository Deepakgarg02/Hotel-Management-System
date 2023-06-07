package com.deepak.microservices.staff.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.staff.model.Staff;
import com.deepak.microservices.staff.repository.StaffRepo;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffRepo staffRepo;

	@Override
	public List<Staff> getAllStaffs() {
		// TODO Auto-generated method stub
		return staffRepo.findAll();
	}

	@Override
	public Optional<Staff> getStaffById(long staffId) {
		// TODO Auto-generated method stub
		return staffRepo.findById(staffId);
	}

	@Override
	public void modifyStaffById(long staffId, Staff staff) {
		// TODO Auto-generated method stub
		staffRepo.save(staff);

	}

	@Override
	public void deleteStaffById(long staffId) {
		// TODO Auto-generated method stub
		staffRepo.deleteById(staffId);
	}

	@Override
	public void addStaff(Staff staff) {
		// TODO Auto-generated method stub
		staffRepo.save(staff);

	}

	@Override
	public List<Staff> getStaffByName(String staffName) {
		// TODO Auto-generated method stub
		return staffRepo.findByStaffName(staffName);

	}

}
