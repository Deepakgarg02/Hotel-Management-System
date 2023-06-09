package com.deepak.microservices.staff.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.staff.model.Staff;
import com.deepak.microservices.staff.service.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	private StaffService staffServiceImpl;

	@GetMapping("/get")
	public List<Staff> getAllStaffs() {
		return staffServiceImpl.getAllStaffs();
	}

	@GetMapping("/get/id/{staffId}")
	public Optional<Staff> getStaffById(@PathVariable String staffId) {
		return staffServiceImpl.getStaffById(staffId);
	}

	@GetMapping("/get/name/{staffName}")
	public List<Staff> getStaffByName(@PathVariable String staffName) {
		return staffServiceImpl.getStaffByName(staffName);
	}

	@DeleteMapping("/delete/{staffId}")
	public String deleteStaffById(@PathVariable String staffId) {
		staffServiceImpl.deleteStaffById(staffId);
		return "Staff Deleted with StaffId " + staffId;
	}

	@PutMapping("/modify/{staffId}")
	public String modifyStaffById(@RequestBody Staff staff, @PathVariable String staffId) {
		staffServiceImpl.modifyStaffById(staffId, staff);
		return "Staff Modified with StaffId " + staffId;
	}

	@PostMapping("/add")
	public String addStaff(@RequestBody Staff staff) {
		staffServiceImpl.addStaff(staff);
		return "Staff Added with StaffId " + staff.getStaffId();
	}

}
