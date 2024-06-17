package org.jsp.reservationapp.controller;

import java.io.IOException;

import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.dto.UserRequest;
import org.jsp.reservationapp.dto.UserResponse;
import org.jsp.reservationapp.service.UserService;
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

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@Valid @RequestBody UserRequest userRequest,
			HttpServletRequest request) {
		return service.saveUser(userRequest, request);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@Valid @RequestBody UserRequest userRequest,
			@PathVariable int id) {
		return service.updateUser(userRequest, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> findById(@PathVariable int id) {
		return service.findById(id);
	}

	@PostMapping("/find-by-phone")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(@RequestParam long phone,
			@RequestParam String password) {
		return service.verifyUser(phone, password);
	}

	@PostMapping("/find-by-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(@RequestParam String email,
			@RequestParam String password) {
		return service.verifyUser(email, password);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable int id) {
		return service.deleteUser(id);
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
		UserResponse userResponse = service.verifyLink(token);

		if (userResponse != null)
			try {
				response.sendRedirect("http://localhost:3000/reset-password");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
