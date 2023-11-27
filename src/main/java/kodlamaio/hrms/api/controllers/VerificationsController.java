package kodlamaio.hrms.api.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.core.utilities.entities.User;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;

@RestController
@RequestMapping("/api/verifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VerificationsController {

	@Autowired
	private IUserDao userDao;

	@GetMapping("/verifyAccount")
	public Result verifyAccount(@RequestParam("token") Long userId) {
		Optional<User> optionalUser = userDao.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (!user.isVerified()) {

				if (user.getVerificationExpiry().isAfter(LocalDateTime.now())) {
					user.setVerified(true);
					userDao.save(user);
					return new SuccessResult("Your account has been successfully verified.");
				} else {
					return new ErrorResult("The verification link has expired!");
				}
			}
			else {
				return new ErrorResult("Your account has already been verified.");
			}
		}
		return new ErrorResult("Verification failed!");
	}

}
