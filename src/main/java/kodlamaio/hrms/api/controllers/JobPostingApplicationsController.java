package kodlamaio.hrms.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.IJobPostingApplicationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobPostingApplication;

@RestController
@RequestMapping("/api/jobPostingApplications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class JobPostingApplicationsController {

	@Autowired
	private IJobPostingApplicationService applicationService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result add(@Valid @RequestBody JobPostingApplication jobPostingApplication) {
		return this.applicationService.add(jobPostingApplication);
	}

	@GetMapping("/getall")
	public DataResult<List<JobPostingApplication>> getAll() {
		return this.applicationService.getAll();
	}

	@GetMapping("/getApplicationById/{applicationId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public DataResult<JobPostingApplication> getApplicationById(@PathVariable Long applicationId) {
		return applicationService.getApplicationById(applicationId);
	}

	@DeleteMapping("/deleteById/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.applicationService.delete(id);
	}

	@GetMapping("/getWithEmployeeId/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public DataResult<List<JobPostingApplication>> findApplicationsByEmployeeId(@PathVariable Long id) {
		return this.applicationService.findApplicationsByEmployeeId(id);
	}

	@GetMapping("/getWithJobPostingId/{jobPostingId}")
	public DataResult<List<JobPostingApplication>> findApplicationsByJobPostingId(@PathVariable Long jobPostingId) {
		return this.applicationService.findApplicationsByJobPostingId(jobPostingId);
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
