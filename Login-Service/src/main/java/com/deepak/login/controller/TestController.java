package com.deepak.login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/receptionist")
	@PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'RECEPTIONIST')")
	public String receptionistAccess() {
		return "Receptionist Content.";
	}

	@GetMapping("/manager")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String managerAccess() {
		return "Manager Board.";
	}

	@GetMapping("/owner")
	@PreAuthorize("hasAuthority('OWNER')")
	public String ownerAccess() {
		return "Owner Board.";
	}
}
