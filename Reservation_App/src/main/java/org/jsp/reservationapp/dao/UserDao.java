package org.jsp.reservationapp.dao;

import java.util.Optional;

import org.jsp.reservationapp.model.User;
import org.jsp.reservationapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	private UserRepository repository;

	public User saveUser(User user) {
		return repository.save(user);
	}

	public Optional<User> findById(int id) {
		return repository.findById(id);
	}

	public Optional<User> verifyUser(long phone, String password) {
		return repository.findByPhoneAndPassword(phone, password);
	}

	public Optional<User> verifyUser(String email, String password) {
		return repository.findByEmailAndPassword(email, password);
	}

	public void deleteUser(int id) {
		repository.deleteById(id);
	}

	public Optional<User> finfByToken(String token) {
		return repository.findByToken(token);
	}

	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
