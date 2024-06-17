package org.jsp.reservationapp.controller;

import java.io.IOException;

import org.jsp.reservationapp.dto.AdminRequest;
import org.jsp.reservationapp.dto.AdminResponse;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.service.AdminService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
	private AdminService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@Valid @RequestBody AdminRequest adminRequest,
			HttpServletRequest request) {
		return service.saveAdmin(adminRequest, request);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@Valid @RequestBody AdminRequest adminRequest,
			@PathVariable int id) {
		return service.updateAdmin(adminRequest, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(@PathVariable int id) {
		return service.findById(id);
	}

	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(@RequestParam long phone,
			@RequestParam String password) {
		return service.verifyAdmin(phone, password);
	}

	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(@RequestParam String email,
			@RequestParam String password) {
		return service.verifyAdmin(email, password);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(@PathVariable int id) {
		return service.deleteAdmin(id);
	}

	@GetMapping("/activate")
	public String activate(@RequestParam String token) {
		return service.activate(token);
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email, HttpServletRequest request) {
		return service.forgotPassword(email, request);
	}

	@GetMapping("/verify-link")
	public void verifyResetPasswordLink(@RequestParam String token, HttpServletResponse response) {
		AdminResponse adminResponse = service.verifyLink(token);

		if (adminResponse != null)
			try {
				response.sendRedirect("http://localhost:3000/reset-password");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
