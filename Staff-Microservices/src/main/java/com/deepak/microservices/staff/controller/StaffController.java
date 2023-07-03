package com.deepak.microservices.staff.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.staff.exception.StaffNotFoundException;
import com.deepak.microservices.staff.model.Staff;
import com.deepak.microservices.staff.service.StaffService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/staff")
//@CrossOrigin(origins = "*")
public class StaffController {

	@Autowired
	private StaffService staffServiceImpl;

	@GetMapping("/get")
	@ApiOperation(value = "Getting All Staff Details")
	public List<Staff> getAllStaffs() {
		return staffServiceImpl.getAllStaffs();
	}

	@GetMapping("/get/id/{staffId}")
	@ApiOperation(value = "Getting Staff Details By Staff Id")
	public Optional<Staff> getStaffById(@PathVariable String staffId) {
		try {
			return staffServiceImpl.getStaffById(staffId);
		} catch (StaffNotFoundException e) {
			return Optional.empty();
		}
	}

	@GetMapping("/get/name/{staffName}")
	@ApiOperation(value = "Getting Staff Details By Staff Name")
	public ResponseEntity<List<Staff>> getStaffByName(@PathVariable String staffName) {
		try {
			List<Staff> staff = staffServiceImpl.getStaffByName(staffName);
			return ResponseEntity.ok(staff);
		} catch (StaffNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{staffId}")
	@ApiOperation(value = "Deleting Staff Details By Staff Id")
	public String deleteStaffById(@PathVariable String staffId) {
		try {
			staffServiceImpl.deleteStaffById(staffId);
			return "Staff Deleted with StaffId " + staffId;
		} catch (StaffNotFoundException e) {
			return "Invalid Staff Id";
		}
	}

	@PutMapping("/modify/{staffId}")
	@ApiOperation(value = "Modifying Staff Details By Staff Id")
	public String modifyStaffById(@RequestBody Staff staff, @PathVariable String staffId) {
		try {
			staffServiceImpl.modifyStaffById(staffId, staff);
			return "Staff Modified";
		} catch (StaffNotFoundException e) {
			return "Invalid Staff Id";
		}
	}

	@PostMapping("/add")
	@ApiOperation(value = "Adding Staff Details")
	public String addStaff(@RequestBody Staff staff) {
		try {
		staffServiceImpl.addStaff(staff);
		return "Added Staff";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}

}
