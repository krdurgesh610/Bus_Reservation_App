package org.jsp.reservationapp.dao;

import java.util.Optional;

import org.jsp.reservationapp.model.Admin;
import org.jsp.reservationapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {
	@Autowired
	private AdminRepository repository;

	public Admin saveAdmin(Admin admin) {
		return repository.save(admin);
	}

	public Optional<Admin> findById(int id) {
		return repository.findById(id);
	}

	public Optional<Admin> verifyAdmin(long phone, String password) {
		return repository.findByPhoneAndPassword(phone, password);
	}

	public Optional<Admin> verifyAdmin(String email, String password) {
		return repository.findByEmailAndPassword(email, password);
	}

	public Optional<Admin> findByToken(String token) {
		return repository.findByToken(token);
	}

	public void deleteAdmin(int id) {
		repository.deleteById(id);
	}

	public Optional<Admin> findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
