package kodlamaio.hrms.business.concretes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IEmployerService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IEmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.dtos.EmployersWithDetailsDto;

@Service
public class EmployerManager implements IEmployerService {

	@Autowired
	private IEmployerDao employerDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private EmailManager emailManager;

	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll());
	}

	@Override
	public Result delete(Long id) {
		this.employerDao.deleteById(id);
		return new SuccessResult("Employer deleted.");
	}

	@Override
	public DataResult<List<EmployersWithDetailsDto>> getEmployersWithDetails() {
		return new SuccessDataResult<List<EmployersWithDetailsDto>>(this.employerDao.getEmployersWithDetails(),
				"Data listed.");
	}

	@Override
	public Result add(Employer employer) {

		if (!userDao.existsByUsername(employer.getUsername())) {

			if (!userDao.existsByEmail(employer.getEmail())) {

				employer.setVerified(false);
				employer.setVerificationExpiry(LocalDateTime.now().plusHours(24));

				this.employerDao.save(employer);

				String verificationLink = "http://localhost:8080/api/verifications/verifyAccount?token="
						+ employer.getId();
				String emailBody = "Please click the link below to verify your account:\n" + verificationLink;
				emailManager.sendEmail(employer.getEmail(), "Account Verification", emailBody);
				return new SuccessResult("Registration successful. Check your email address to verify your account.");

			} else {
				return new ErrorResult("Email already in use!");
			}
		} else {
			return new ErrorResult("Username already in use!");
		}

	}

	@Override
	public Result updateEmployer(Employer newEmployer) {
		Employer existingEmployer = employerDao.findById(newEmployer.getId()).orElse(null);

		if (!newEmployer.getUsername().equals(existingEmployer.getUsername())) {
			if (userDao.existsByUsername(newEmployer.getUsername())) {
				return new ErrorResult("Username already in use!");
			} else {
				existingEmployer.setUsername(newEmployer.getUsername());
			}
		}

		if (!newEmployer.getEmail().equals(existingEmployer.getEmail())) {
			if (userDao.existsByEmail(newEmployer.getEmail())) {
				return new ErrorResult("Email already in use!");
			} else {
				existingEmployer.setEmail(newEmployer.getEmail());
			}
		}
		
		existingEmployer.setCompanyName(newEmployer.getCompanyName());
		existingEmployer.setWebsite(newEmployer.getWebsite());
		existingEmployer.setPhoneNumber(newEmployer.getPhoneNumber());

		employerDao.save(existingEmployer);
		return new SuccessResult("Resume updated successfully.");
	}

	@Override
	public DataResult<Employer> getEmployerById(Long employerId) {
		return new SuccessDataResult<Employer>(this.employerDao.findById(employerId).orElseThrow());
	}

}
