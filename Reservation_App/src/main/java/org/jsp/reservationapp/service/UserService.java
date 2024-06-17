package org.jsp.reservationapp.service;

import java.util.Optional;

import org.jsp.reservationapp.dao.UserDao;
import org.jsp.reservationapp.dto.EmailConfiguration;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.dto.UserRequest;
import org.jsp.reservationapp.dto.UserResponse;
import org.jsp.reservationapp.exception.UserNotFoundException;
import org.jsp.reservationapp.model.User;
import org.jsp.reservationapp.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private ReservationApiMailService mailService;

	@Autowired
	private LinkGeneratorService linkGeneratorService;

	@Autowired
	private EmailConfiguration emailConfiguration;

	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest,
			HttpServletRequest request) {
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		User user = mapToUser(userRequest);
		user.setStatus(AccountStatus.IN_ACTIVE.toString());
		user = userDao.saveUser(user);
		String activation_link = linkGeneratorService.getActivationLink(user, request);
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration
				.setText("Dear User Please Activate Your Account by Clicking on Following link :" + activation_link);
		emailConfiguration.setToAddress(user.getEmail());
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setData(mapToUserResponse(user));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest, int id) {
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		Optional<User> rec = userDao.findById(id);

		if (rec.isPresent()) {
			User db = rec.get();
			db.setAge(userRequest.getAge());
			db.setEmail(userRequest.getEmail());
			db.setGender(userRequest.getGender());
			db.setName(userRequest.getName());
			db.setPhone(userRequest.getPhone());
			db.setPassword(userRequest.getPassword());
			structure.setMessage("User Updated");
			structure.setData(mapToUserResponse(userDao.saveUser(db)));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new UserNotFoundException("Can't Update User as Id is INvalid");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> findById(int id) {
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		Optional<User> rec = userDao.findById(id);

		if (rec.isPresent()) {
			structure.setMessage("User Found");
			structure.setData(mapToUserResponse(rec.get()));
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid User Id");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(long phone, String password) {
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		Optional<User> rec = userDao.verifyUser(phone, password);

		if (rec.isPresent()) {
			User user = rec.get();

			if (user.getStatus().equals(AccountStatus.IN_ACTIVE.toString())) {
				throw new IllegalStateException("Please Activate Your Account before you Sign In");
			}

			structure.setData(mapToUserResponse(rec.get()));
			structure.setMessage("User Verified");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Can't Verified User as Phone Number and Password Invalid");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(String email, String password) {
		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		Optional<User> rec = userDao.verifyUser(email, password);

		if (rec.isPresent()) {
			structure.setData(mapToUserResponse(rec.get()));
			structure.setMessage("User Verified");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Can't Verified User as Email Id and Password Invalid");
	}

	public ResponseEntity<ResponseStructure<String>> deleteUser(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<User> rec = userDao.findById(id);

		if (rec.isPresent()) {
			userDao.deleteUser(id);
			structure.setMessage("User Deleted");
			structure.setData("User Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Can't Delete User as Id is Invalid");
	}

	private User mapToUser(UserRequest userRequest) {
		return User.builder().name(userRequest.getName()).phone(userRequest.getPhone()).email(userRequest.getEmail())
				.age(userRequest.getAge()).gender(userRequest.getGender()).password(userRequest.getPassword()).build();
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().id(user.getId()).name(user.getName()).phone(user.getPhone())
				.email(user.getEmail()).age(user.getAge()).gender(user.getGender()).password(user.getPassword())
				.build();
	}

	public String activate(String token) {
		Optional<User> rec = userDao.finfByToken(token);

		if (rec.isEmpty()) {
			throw new UserNotFoundException("Invalid Token");
		}
		User db = rec.get();
		db.setStatus("ACTIVE");
		db.setToken(null);
		userDao.saveUser(db);
		return "Your Account has been Activated";
	}

	public String forgotPassword(String email, HttpServletRequest request) {
		Optional<User> rec = userDao.findByEmail(email);

		if (rec.isEmpty())
			throw new UserNotFoundException("Invalid Email Id");
		User user = rec.get();
		String resetPasswordLink = linkGeneratorService.getResetPasswordLink(user, request);
		emailConfiguration.setToAddress(email);
		emailConfiguration.setText("Please Click on the following link to reset your Password :" + resetPasswordLink);
		emailConfiguration.setSubject("RESET YOUR PASSWORD");
		mailService.sendMail(emailConfiguration);
		return "reset password link has been sent to entered email Id";
	}

	public UserResponse verifyLink(String token) {
		Optional<User> rec = userDao.finfByToken(token);

		if (rec.isEmpty())
			throw new UserNotFoundException("Link has been expired or it is Invalid");
		User db = rec.get();
		db.setToken(null);
		userDao.saveUser(db);
		return mapToUserResponse(db);
	}

}
