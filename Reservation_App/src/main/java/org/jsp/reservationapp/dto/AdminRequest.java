package org.jsp.reservationapp.dto;

import java.util.List;

import org.jsp.reservationapp.model.Bus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRequest {
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	private long phone;
	
	@Email(message = "Invalid Format")
	private String email;
	
	@NotBlank(message = "GST Number is mandatory")
	@Size(min = 7,max = 15,message = "GST Number must have 7 character")
	private String gst_number;
	
	@NotBlank(message = "Travels Name mandatory")
	private String travels_name;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8,max = 15,message = "Password length must be between 8 to 15")
	private String password;
	
	private List<Bus> bus;
}
