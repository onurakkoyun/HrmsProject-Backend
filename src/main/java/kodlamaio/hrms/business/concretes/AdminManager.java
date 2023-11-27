package kodlamaio.hrms.business.concretes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IAdminService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IAdminDao;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;
import kodlamaio.hrms.entities.concretes.Admin;

@Service
public class AdminManager implements IAdminService {

	@Autowired
	private IAdminDao adminDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private EmailManager emailManager;

	@Override
	public DataResult<List<Admin>> getAll() {
		return new SuccessDataResult<List<Admin>>(this.adminDao.findAll(), "Admins listed.");
	}

	@Override
	public Result add(Admin admin) {

		if (userDao.existsByUsername(admin.getUsername())) {
			return new ErrorResult("Username already in use!");
		}

		if (userDao.existsByEmail(admin.getEmail())) {
			return new ErrorResult("Email already in use!");
		}

		admin.setVerified(false);
		admin.setVerificationExpiry(LocalDateTime.now().plusHours(24));

		this.adminDao.save(admin);

		String verificationLink = "http://localhost:8080/api/verifications/verifyAccount?token=" + admin.getId();
		String emailBody = "Hesabınızı doğrulamak için lütfen aşağıdaki linke tıklayın:\n" + verificationLink;
		emailManager.sendEmail(admin.getEmail(), "Account Verification", emailBody);
		return new SuccessResult("Kayıt işlemi başarılı. Hesabınızı doğrulamak için e-posta adresinizi kontrol edin.");
	}

	@Override
	public Result delete(int id) {
		this.adminDao.deleteById(id);
		return new SuccessResult("Admin deleted.");
	}

}
