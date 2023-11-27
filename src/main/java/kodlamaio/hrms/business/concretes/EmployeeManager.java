package kodlamaio.hrms.business.concretes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IEmployeeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IEmployeeDao;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.dtos.EmployeesWithDetailsDto;
import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class EmployeeManager implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private EmailManager emailManager;

	@Override
	public DataResult<List<Employee>> getAll() {
		return new SuccessDataResult<List<Employee>>(this.employeeDao.findAll());
	}

	@Override
	public Result delete(Long id) {
		this.employeeDao.deleteById(id);
		return new SuccessResult("Employee deleted.");
	}

	@Override
	public DataResult<List<EmployeesWithDetailsDto>> getEmployeesWithDetails() {
		return new SuccessDataResult<List<EmployeesWithDetailsDto>>(this.employeeDao.getEmployeesWithDetails());
	}

	@Override
	public DataResult<Employee> getEmployeeById(Long employeeId) {
		return new SuccessDataResult<Employee>(this.employeeDao.findById(employeeId).orElseThrow());
	}

	@Override
	public Result add(Employee employee) {

		if (userDao.existsByUsername(employee.getUsername())) {
			return new ErrorResult("Username already in use!");
		}
		if (userDao.existsByEmail(employee.getEmail())) {
			return new ErrorResult("Email already in use!");
		}
		if (employeeDao.existsEmployeeByIdentityNumber(employee.getIdentityNumber())) {
			return new ErrorResult("Identity number already in use!");
		}

		/*
		 * if (!checkIfRealPerson(employee)) { return new
		 * ErrorResult("The credentials are invalid!"); }
		 */
		employee.setVerified(false);
		employee.setVerificationExpiry(LocalDateTime.now().plusHours(24));
		System.out.println(employee.getVerificationExpiry());

		this.employeeDao.save(employee);

		String verificationLink = "http://localhost:8080/api/verifications/verifyAccount?token=" + employee.getId();
		String emailBody = "Please click the link below to verify your account:\n" + verificationLink;
		emailManager.sendEmail(employee.getEmail(), "Account Verification", emailBody);
		return new SuccessResult("Registration successful.\nCheck your email address to verify your account.");
	}

	@Override
	public Result updateEmployee(Employee newEmployee) {
		Employee existingEmployee = employeeDao.findById(newEmployee.getId()).orElse(null);

		if (!newEmployee.getUsername().equals(existingEmployee.getUsername())) {
			if (userDao.existsByUsername(newEmployee.getUsername())) {
				return new ErrorResult("Username already in use!");
			} else {
				existingEmployee.setUsername(newEmployee.getUsername());
			}
		}

		if (!newEmployee.getEmail().equals(existingEmployee.getEmail())) {
			if (userDao.existsByEmail(newEmployee.getEmail())) {
				return new ErrorResult("Email already in use!");
			} else {
				existingEmployee.setEmail(newEmployee.getEmail());
			}
		}

		existingEmployee.setFirstName(newEmployee.getFirstName());
		existingEmployee.setLastName(newEmployee.getLastName());
		existingEmployee.setPhoneNumber(newEmployee.getPhoneNumber());
		existingEmployee.setCountry(newEmployee.getCountry());
		existingEmployee.setProvince(newEmployee.getProvince());
		existingEmployee.setCity(newEmployee.getCity());
		existingEmployee.setPostalCode(newEmployee.getPostalCode());
		existingEmployee.setAddress(newEmployee.getAddress());

		employeeDao.save(existingEmployee);
		return new SuccessResult("Resume updated successfully.");
	}

	@Override
	public boolean checkIfRealPerson(Employee employee) {

		KPSPublicSoapProxy client = new KPSPublicSoapProxy();

		try {
			boolean result = client.TCKimlikNoDogrula(Long.parseLong(employee.getIdentityNumber()),
					employee.getFirstName().toUpperCase(), employee.getLastName().toUpperCase(),
					employee.getYearOfBirth());

			if (result) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
		}
		return false;
	}

}
