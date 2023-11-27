package kodlamaio.hrms.business.concretes;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IEmailService;

@Service
public class EmailManager implements IEmailService{
		
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Override
	public String sendEmail(String to, String subject, String body) {
		
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.prot", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
            	return new PasswordAuthentication(fromEmail, password);
            }
        }
        );
        
        try {
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return "Mail sended.";

        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
		
		
	}
	
	
}
