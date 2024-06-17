package org.jsp.reservationapp.service;

import java.util.Optional;

import org.jsp.reservationapp.dao.AdminDao;
import org.jsp.reservationapp.dto.AdminRequest;
import org.jsp.reservationapp.dto.AdminResponse;
import org.jsp.reservationapp.dto.EmailConfiguration;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.exception.AdminNotFoundException;
import org.jsp.reservationapp.model.Admin;
import org.jsp.reservationapp.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private ReservationApiMailService mailService;

	@Autowired
	private LinkGeneratorService linkGeneratorService;

	@Autowired
	private EmailConfiguration emailConfiguration;

	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest,
			HttpServletRequest request) {
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();

		Admin admin = mapToAdmin(adminRequest);

//		admin.setStatus("IN_ACTIVE");
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());

//		adminDao.saveAdmin(admin);
		admin = adminDao.saveAdmin(admin);

//		String activation_link = siteUrl.replace(path, "/api/admins/activate");
		String activation_link = linkGeneratorService.getActivationLink(admin, request);

		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration
				.setText("Dear Admin Please Activate Your Account by clicking on following Link :" + activation_link);
		emailConfiguration.setToAddress(admin.getEmail());

//		structure.setMessage(mailService.sendMail(admin.getEmail(), activation_link));
		structure.setMessage(mailService.sendMail(emailConfiguration));

//		structure.setData(mapToAdminResponse(admin));
		structure.setData(mapToAdminResponse(admin));

//		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setStatusCode(HttpStatus.CREATED.value());

//		String siteUrl = request.getRequestURL().toString();
//		String path = request.getServletPath();
//
//		String token = RandomString.make(45);
//		activation_link += "?token=" + token;
//		System.out.println(activation_link);
//		admin.setToken(token);

		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}

	/**
	 * This method will accept AdminRequest(DTO) and Admin Id and Update the Admin
	 * in the Database if identifier is valid.
	 * 
	 * @param AdminRequest
	 * @param int
	 * @throw {@code AdminNotFoundException} if Identifier is Invalid
	 */

	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest, int id) {
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		Optional<Admin> rec = adminDao.findById(id);

		if (rec.isPresent()) {
			Admin db = rec.get();
			db.setEmail(adminRequest.getEmail());
			db.setGst_number(adminRequest.getGst_number());
			db.setName(adminRequest.getName());
			db.setPhone(adminRequest.getPhone());
			db.setPassword(adminRequest.getPassword());
			db.setTravels_name(adminRequest.getTravels_name());
			structure.setData(mapToAdminResponse(adminDao.saveAdmin(db)));
			structure.setMessage("Admin Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Can't Update Admin as Id is Invalid");
	}

	public ResponseEntity<ResponseStructure<AdminResponse>> findById(int id) {
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		Optional<Admin> db = adminDao.findById(id);

		if (db.isPresent()) {
			structure.setData(mapToAdminResponse(db.get()));
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Admin Id");
	}

	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(long phone, String password) {
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		Optional<Admin> db = adminDao.verifyAdmin(phone, password);

		if (db.isPresent()) {

			Admin admin = db.get();
			if (admin.getStatus().equals(AccountStatus.IN_ACTIVE.toString())) {
				throw new IllegalStateException("Please Activate Your Account before You Sign In");
			}
			structure.setData(mapToAdminResponse(admin));
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Phone Number and Password");
	}

	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(String email, String password) {
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		Optional<Admin> db = adminDao.verifyAdmin(email, password);

		if (db.isPresent()) {
			structure.setData(mapToAdminResponse(db.get()));
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Email Id and Password");
	}

	public ResponseEntity<ResponseStructure<String>> deleteAdmin(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Admin> db = adminDao.findById(id);

		if (db.isPresent()) {
			adminDao.deleteAdmin(id);
			structure.setMessage("Admin Deleted");
			structure.setData("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Can't Delete Admin as Id is Invalid");
	}

	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().name(adminRequest.getName()).phone(adminRequest.getPhone())
				.email(adminRequest.getEmail()).gst_number(adminRequest.getGst_number())
				.travels_name(adminRequest.getTravels_name()).password(adminRequest.getPassword()).build();
	}

	private AdminResponse mapToAdminResponse(Admin admin) {
		return AdminResponse.builder().id(admin.getId()).name(admin.getName()).phone(admin.getPhone())
				.email(admin.getEmail()).gst_number(admin.getGst_number()).travels_name(admin.getTravels_name())
				.password(admin.getPassword()).build();
	}

	public String activate(String token) {
		Optional<Admin> rec = adminDao.findByToken(token);

		if (rec.isEmpty()) {
			throw new AdminNotFoundException("Invalid Token");
		}
		Admin db = rec.get();
		db.setStatus("ACTIVE");
		db.setToken(null);
		adminDao.saveAdmin(db);
		return "Your Account has been Activated";
	}

	public String forgotPassword(String email, HttpServletRequest request) {
		Optional<Admin> rec = adminDao.findByEmail(email);

		if (rec.isEmpty())
			throw new AdminNotFoundException("Invalid Email Id");
		Admin admin = rec.get();
		String resetPasswordLink = linkGeneratorService.getResetPasswordLink(admin, request);
		emailConfiguration.setToAddress(email);
		emailConfiguration.setText("Please click on the following link to reset your password :" + resetPasswordLink);
		emailConfiguration.setSubject("RESET YOUR PASSWORD");
		mailService.sendMail(emailConfiguration);
		return "reset password link has been sent to entered email Id";
	}

	public AdminResponse verifyLink(String token) {
		Optional<Admin> rec = adminDao.findByToken(token);

		if (rec.isEmpty())
			throw new AdminNotFoundException("Link has been expired or it is Invalid");
		Admin dbAdmin = rec.get();
		dbAdmin.setToken(null);
		adminDao.saveAdmin(dbAdmin);
		return mapToAdminResponse(dbAdmin);
	}
}
