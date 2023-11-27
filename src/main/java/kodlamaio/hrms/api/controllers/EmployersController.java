package kodlamaio.hrms.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.IEmployerService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.dtos.EmployersWithDetailsDto;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployersController {

	@Autowired
	private IEmployerService employerService;

	@GetMapping("/getall")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<Employer>> getAll() {
		return this.employerService.getAll();
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @RequestBody Employer employer) {
		return ResponseEntity.ok(this.employerService.add(employer));
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public Result updateEmployer(@RequestBody Employer newEmployer) {
		return employerService.updateEmployer(newEmployer);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.employerService.delete(id);
	}

	@GetMapping("/getEmployerById/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<Employer> getEmployerById(@PathVariable Long id) {
		return employerService.getEmployerById(id);
	}

	@GetMapping("/getEmployersWithDetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<List<EmployersWithDetailsDto>> getEmployersWithDetails() {
		return this.employerService.getEmployersWithDetails();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
		Map<String, String> validationErrors = new HashMap<String, String>();

		for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama Hataları");
		return errors;
	}
}
