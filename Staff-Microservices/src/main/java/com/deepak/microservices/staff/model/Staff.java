package com.deepak.microservices.staff.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long staffId;

	@NotNull(message = "Staff Name cannnot be Null")
	@Size(min = 3, message = "Staff Name should be minimum of 3 digits")
	private String staffName;

	@NotNull(message = "Staff Address cannnot be Null")
	@Size(min = 5, message = "Staff Address should be minimum of 5 digits")
	private String staffAddress;

	private String staffSalary;

	private int staffAge;

	@NotNull(message = "Staff MailId cannnot be Null")
	@Size(min = 12, message = "Staff MailId should be minimum of 12 digits")
	private String staffEmail;

	public Staff(long staffId, String staffName, String staffAddress, String staffSalary, int staffAge,
			String staffEmail) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffAddress = staffAddress;
		this.staffSalary = staffSalary;
		this.staffAge = staffAge;
		this.staffEmail = staffEmail;
	}

	public Staff() {

	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

	public String getStaffSalary() {
		return staffSalary;
	}

	public void setStaffSalary(String staffSalary) {
		this.staffSalary = staffSalary;
	}

	public int getStaffAge() {
		return staffAge;
	}

	public void setStaffAge(int staffAge) {
		this.staffAge = staffAge;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}

}
