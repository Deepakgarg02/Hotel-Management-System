package com.deepak.microservices.staff.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.microservices.staff.exception.StaffNotFoundException;
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
	public Optional<Staff> getStaffById(String staffId) throws StaffNotFoundException {
		// TODO Auto-generated method stub
		return staffRepo.findById(staffId);
	}

	@Override
	public void modifyStaffById(String staffId, Staff staff) throws StaffNotFoundException {
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
	public void deleteStaffById(String staffId) throws StaffNotFoundException {
		// TODO Auto-generated method stub
		staffRepo.deleteById(staffId);
	}

	@Override
	public void addStaff(Staff staff) {
		// TODO Auto-generated method stub
	    // Check if inventoryName is null or empty
	    if (staff.getStaffName() == null || staff.getStaffName().isEmpty() 
	    	|| staff.getStaffAddress() == null || staff.getStaffAddress().isEmpty()
	    	|| staff.getStaffEmail() == null || staff.getStaffEmail().isEmpty()) {
	        throw new IllegalArgumentException("Fields can't be Null");
	    }else {
		staffRepo.save(staff);
	    }
	}

	@Override
	public List<Staff> getStaffByName(String staffName) throws StaffNotFoundException {
		// TODO Auto-generated method stub
		if (!staffName.matches("^[a-zA-Z\\s]+$")) {
	        throw new StaffNotFoundException("Invalid Staff Name");
	    }
		List<Staff> staff = staffRepo.findByStaffName(staffName);
		return staff;

	}

}
