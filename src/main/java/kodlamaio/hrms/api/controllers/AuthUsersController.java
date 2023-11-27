package kodlamaio.hrms.api.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.validation.Valid;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.IAdminService;
import kodlamaio.hrms.business.abstracts.IEmployeeService;
import kodlamaio.hrms.business.abstracts.IEmployerService;
import kodlamaio.hrms.business.concretes.RefreshTokenManager;
import kodlamaio.hrms.core.utilities.entities.ERole;
import kodlamaio.hrms.core.utilities.entities.RefreshToken;
import kodlamaio.hrms.core.utilities.entities.Role;
import kodlamaio.hrms.core.utilities.entities.User;
import kodlamaio.hrms.dataAccess.abstracts.IRoleDao;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;
import kodlamaio.hrms.entities.concretes.Admin;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.exceptions.TokenRefreshException;
import kodlamaio.hrms.payload.request.LoginRequest;
import kodlamaio.hrms.payload.request.SignupAdminRequest;
import kodlamaio.hrms.payload.request.SignupEmployeeRequest;
import kodlamaio.hrms.payload.request.SignupEmployerRequest;
import kodlamaio.hrms.payload.request.SignupRequest;
import kodlamaio.hrms.payload.request.TokenRefreshRequest;
import kodlamaio.hrms.payload.response.JwtResponse;
import kodlamaio.hrms.payload.response.MessageResponse;
import kodlamaio.hrms.payload.response.TokenRefreshResponse;
import kodlamaio.hrms.security.jwt.JwtUtils;
import kodlamaio.hrms.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthUsersController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserDao userDao;

	@Autowired
	IAdminService adminService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	IEmployerService employerService;

	@Autowired
	IRoleDao roleDao;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenManager refreshTokenManager;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		String input = loginRequest.getUsername();

		String username = "";

		Optional<User> optionalUser;

		if (input.contains("@")) {
			if (isValidEmail(input)) {
				optionalUser = userDao.findByEmail(input);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Email is not in the correct format!"));
			}
		} else {
			optionalUser = userDao.findByUsername(input);
		}

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			username = user.getUsername(); // Optional nesnesinden kullanıcı adını alıyoruz
			if (!user.isVerified()) {

				if (user.getVerificationExpiry().isBefore(LocalDateTime.now())) {
					userDao.deleteById(user.getId());
					return ResponseEntity.badRequest()
							.body(new MessageResponse("The verification link has expired. Please sign up again."));
				}

				return ResponseEntity.badRequest().body(new MessageResponse("Email verification not done yet!"));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email/Username Not Found"));
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwt = jwtUtils.generateJwtToken(userDetails);
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenManager.createRefreshToken(userDetails.getId());

		return ResponseEntity
				.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
						userDetails.getEmail(), userDetails.getProfilePhotoUrl(), userDetails.getPhoneNumber(), roles));

	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenManager.findByToken(requestRefreshToken).map(refreshTokenManager::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	private boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return Pattern.compile(regex).matcher(email).matches();
	}

	@PostMapping("/signup/user")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userDao.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDao.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User newUser = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "employee":
					Role employeeRole = roleDao.findByName(ERole.ROLE_EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employeeRole);
					break;
				case "employer":
					Role employerRole = roleDao.findByName(ERole.ROLE_EMPLOYER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employerRole);
					break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		newUser.setRoles(roles);
		userDao.save(newUser);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signup/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupAdminRequest signUpAdminRequest) {

		Admin newAdmin = new Admin(signUpAdminRequest.getUsername(), signUpAdminRequest.getEmail(),
				encoder.encode(signUpAdminRequest.getPassword()), signUpAdminRequest.getFirstName(),
				signUpAdminRequest.getLastName(), signUpAdminRequest.getPhone());

		Set<String> strRoles = signUpAdminRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "employee":
					Role employeeRole = roleDao.findByName(ERole.ROLE_EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employeeRole);
					break;
				case "employer":
					Role employerRole = roleDao.findByName(ERole.ROLE_EMPLOYER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employerRole);
					break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		newAdmin.setRoles(roles);
		return ResponseEntity.ok(this.adminService.add(newAdmin));
	}

	@PostMapping("/signup/employee")
	public ResponseEntity<?> registerEmployee(@Valid @RequestBody SignupEmployeeRequest signUpEmployeeRequest) {

		Employee newEmployee = new Employee(signUpEmployeeRequest.getUsername(), signUpEmployeeRequest.getEmail(),
				encoder.encode(signUpEmployeeRequest.getPassword()), signUpEmployeeRequest.getProfilePhotoUrl(),
				signUpEmployeeRequest.getVerificationExpiry(), signUpEmployeeRequest.isVerified(),
				signUpEmployeeRequest.getFirstName(), signUpEmployeeRequest.getLastName(),
				signUpEmployeeRequest.getIdentityNumber(), signUpEmployeeRequest.getYearOfBirth());

		Set<String> strRoles = signUpEmployeeRequest.getRole();
		Set<Role> roles = new HashSet<>();

		Role employeeRole = roleDao.findByName(ERole.ROLE_EMPLOYEE)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(employeeRole);

		if (strRoles == null) {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "employer":
					Role employerRole = roleDao.findByName(ERole.ROLE_EMPLOYER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employerRole);
					break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		newEmployee.setRoles(roles);
		return ResponseEntity.ok(this.employeeService.add(newEmployee));
	}

	@PostMapping("/signup/employer")
	public ResponseEntity<?> registerEmployer(@Valid @RequestBody SignupEmployerRequest signupEmployerRequest) {

		Employer newEmployer = new Employer(signupEmployerRequest.getUsername(), signupEmployerRequest.getEmail(),
				encoder.encode(signupEmployerRequest.getPassword()), signupEmployerRequest.getProfilePhotoUrl(),
				signupEmployerRequest.getVerificationExpiry(), signupEmployerRequest.isVerified(),
				signupEmployerRequest.getCompanyName(), signupEmployerRequest.getWebsite(),
				signupEmployerRequest.getPhoneNumber());

		Set<String> strRoles = signupEmployerRequest.getRole();
		Set<Role> roles = new HashSet<>();

		Role employerRole = roleDao.findByName(ERole.ROLE_EMPLOYER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(employerRole);

		if (strRoles == null) {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "employee":
					Role employeeRole = roleDao.findByName(ERole.ROLE_EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employeeRole);
					break;
				default:
					Role userRole = roleDao.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		newEmployer.setRoles(roles);
		return ResponseEntity.ok(this.employerService.add(newEmployer));
	}

}
