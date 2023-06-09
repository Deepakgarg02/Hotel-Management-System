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
	public Optional<Staff> getStaffById(String staffId) {
		// TODO Auto-generated method stub
		return staffRepo.findById(staffId);
	}

	@Override
	public void modifyStaffById(String staffId, Staff staff) {
		// TODO Auto-generated method
		Optional<Staff> existingStaffOptional = staffRepo.findById(staffId);

		if (existingStaffOptional.isPresent()) {
			Staff existingStaff = existingStaffOptional.get();
			
			// Update the necessary fields of the existing staff record
 			existingStaff.setStaffName(staff.getStaffName());
			existingStaff.setStaffAge(staff.getStaffAge());
			existingStaff.setStaffAddress(staff.getStaffAddress());
			existingStaff.setStaffSalary(staff.getStaffSalary());
			existingStaff.setStaffEmail(staff.getStaffEmail());
			staffRepo.save(existingStaff);
		}
	}

	@Override
	public void deleteStaffById(String staffId) {
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
