package org.jsp.reservationapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ReservationApiMailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String sendMail(String mail,String url) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(mail);
		simpleMailMessage.setText("Dear user, Please activate your account"+url);
		simpleMailMessage.setSubject("Activate your Account");
		javaMailSender.send(simpleMailMessage);
		return "Registration successful and Verification mail has been send";
	}
}