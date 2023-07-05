package com.deepak.microservices.staff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.deepak.microservices.staff.exception.StaffNotFoundException;
import com.deepak.microservices.staff.model.Staff;
import com.deepak.microservices.staff.repository.StaffRepo;
import com.deepak.microservices.staff.service.StaffService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StaffMicroservicesApplicationTests {

	@MockBean
	private StaffRepo staffRepo;

	@Autowired
	private StaffService staffService;

	@Test
    public void getAllStaffsTest() {
	 when(staffRepo.findAll()).thenReturn(Stream
			 .of(new Staff("1234567", "Deepak", "Kalayat", "Manager", 15000.0, 25, "dgargkyt@gmail.com"),
			 new Staff("1234567", "Sumit", "Kaithal", "Manager", 15000.0, 25, "sumit@gmail.com"))
			 .collect(Collectors.toList()));	 
	 
	 List<Staff> staff = staffService.getAllStaffs();
	 assertEquals(2, staff.size());
 }

	@Test
	public void addStaffTest() {
		Staff staff = new Staff("1234567", "Deepak", "Kalayat","Manager", 15000.0, 25, "dgargkyt@gmail.com");
		staffService.addStaff(staff);
		verify(staffRepo, times(1)).save(staff);
	}

	@Test
	public void deleteStaffByIdTest() throws StaffNotFoundException {
		String staffId = "1234567";
		staffService.deleteStaffById(staffId);
		verify(staffRepo, times(1)).deleteById(staffId);
	}

	@Test
	public void getStaffByIdTest() throws StaffNotFoundException {
		String staffId = "1234567";
		staffService.getStaffById(staffId);
		verify(staffRepo, times(1)).findById(staffId);
	}
	
	@Test
	public void getStaffByNameTest() throws StaffNotFoundException {
		String staffName = "Deepak";
		staffService.getStaffByName(staffName);
		verify(staffRepo, times(1)).findByStaffName(staffName);
	}

	@Test
	public void modifyStaffByIdTest() throws StaffNotFoundException {
		// Create a Staff object with updated values
		Staff updatedStaff = new Staff("1234568", "Updated", "Updated","Updated", 15000.0, 50, "dgargkyt@gmail.com");

		// Mock the existing Staff record
		Staff existingStaff = new Staff("1234568", "Deepak", "Kalayat","Manager", 15000.0, 25, "dgargkyt@gmail.com");
		Optional<Staff> existingStaffOptional = Optional.of(existingStaff);
		when(staffRepo.findById("1234568")).thenReturn(existingStaffOptional);

		// Call the modifyStaffById method
		staffService.modifyStaffById("1234568", updatedStaff);

		// Verify that staffRepo.save() is called with the updated Staff
		verify(staffRepo, times(1)).save(existingStaff);

		// Assert that the fields of the existing Staff have been updated
		assertEquals("Updated", existingStaff.getStaffName());
		assertEquals(50, existingStaff.getStaffAge());
	}

}
