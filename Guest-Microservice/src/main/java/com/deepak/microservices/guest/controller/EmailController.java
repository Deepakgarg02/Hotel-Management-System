package com.deepak.microservices.guest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.microservices.guest.model.Email;
import com.deepak.microservices.guest.service.EmailServiceImpl;

@RestController
@RequestMapping("/guest/email")
//@CrossOrigin(origins = "*")
public class EmailController {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@GetMapping("/welcome")
	public String welcome() {
		return "Hello Deepak";
	}

	@PostMapping("/send")
	public ResponseEntity<?> sendEmail(@RequestBody Email email) {
		try {
			emailServiceImpl.sendEmail(email.getTo());
			return ResponseEntity.ok("Sended Email to: "+ email.getTo() );
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email Cann't Sent.....");
		}
	}

}
