package kodlamaio.hrms.business.abstracts;


public interface IEmailService {

	String sendEmail(String to, String subject, String body);

}
