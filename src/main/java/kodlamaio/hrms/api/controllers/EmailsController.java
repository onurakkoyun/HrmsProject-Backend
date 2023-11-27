package kodlamaio.hrms.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.IEmailService;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailsController {
	
	@Autowired
	private IEmailService emailService;

	@PostMapping("/send")
	public String sendEmail(String to, String subject, String body) {
		return emailService.sendEmail(to, subject, body);
	}

}
