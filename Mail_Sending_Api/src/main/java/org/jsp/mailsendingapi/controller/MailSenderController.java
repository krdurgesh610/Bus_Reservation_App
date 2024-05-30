package org.jsp.mailsendingapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class MailSenderController {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${activation.token}")
	private String token;

	@PostMapping("/send-mail")
	public String sendMail(@RequestParam String mailId, HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String path = request.getServletPath();
		String activation_link = url.replace(path, "/api/activate");
		activation_link = activation_link+"?token="+token;
		System.out.println(activation_link);
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(mailId);
			helper.setSubject("Testing the Mail Sending api");
			helper.setText("Dear User, we are sending this mail to test our Mail Sending api."+activation_link);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		javaMailSender.send(message);
		return "Mail has been sent to :" + mailId;
	}
	
	@PostMapping("/activate")
	public String activate(@RequestParam String token) {
		if(token.equals(token)) {
			return "Your Account has been activated";
		}
		return "can't activate account beacuse verification link is Invalid";
	}

}
