package org.jsp.reservationapp.service;

import java.util.Optional;

import org.jsp.reservationapp.dao.AdminDao;
import org.jsp.reservationapp.dto.AdminRequest;
import org.jsp.reservationapp.dto.AdminResponse;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.exception.AdminNotFoundException;
import org.jsp.reservationapp.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ReservationApiMailService mailService;

	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest, HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		String path = request.getServletPath();
		String activation_link = siteUrl.replace(path, "/api/admins/activate");
		ResponseStructure<AdminResponse> structure = new ResponseStructure<>();
		String token = RandomString.make(45);
		activation_link += "?token="+token;
		System.out.println(activation_link);
		Admin admin = mapToAdmin(adminRequest);
		admin.setToken(token);
		admin.setStatus("IN_ACTIVE");
		adminDao.saveAdmin(admin);
		structure.setMessage(mailService.sendMail(admin.getEmail(), activation_link));
		structure.setData(mapToAdminResponse(admin));
		structure.setStatusCode(HttpStatus.CREATED.value());
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
			structure.setData(mapToAdminResponse(db.get()));
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
		return "Your Account has been activated";
	}
}
