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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import kodlamaio.hrms.business.abstracts.IJobPostingService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto;

@RestController
@RequestMapping("/api/jobPostings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class JobPostingsController {

	@Autowired
	private IJobPostingService jobPostingService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public ResponseEntity<?> addJobPosting(@Valid @RequestBody JobPosting jobPosting) {
		return ResponseEntity.ok(this.jobPostingService.add(jobPosting));
	}

	@DeleteMapping("/deleteByJobPostingId/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.jobPostingService.delete(id);
	}

	@GetMapping("/getalljobpostings")
	public DataResult<List<JobPosting>> getAllJobPosting() {
		return this.jobPostingService.getAllJobPosting();
	}

	@GetMapping("/getActiveJobPostingsWithDetails")
	public DataResult<List<JobPostingsWithDetailsDto>> getActiveJobPostingsWithDetails() {
		return this.jobPostingService.getActiveJobPostingsWithDetails();
	}

	@PostMapping("/updateActivityById")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public Result updateActivityById(@RequestParam("jobPostingId") Long jobPostingId,
			@RequestParam("isActive") boolean activity) {
		return jobPostingService.updateActivityById(activity, jobPostingId);
	}

	@GetMapping("/getJobPostingsSortByDate")
	public DataResult<List<JobPostingsWithDetailsDto>> getJobPostingsSortByDate() {
		return this.jobPostingService.getJobPostingsSortByDate();
	}
	
	@GetMapping("/getJobPostingById/{jobPostingId}")
	public DataResult<JobPosting> getJobPostingById(@PathVariable Long jobPostingId) {
		return jobPostingService.getJobPostingById(jobPostingId);
	}
	
	@GetMapping("/getJobPostingsByEmployerId/{employerId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<List<JobPosting>> getJobPostingsByEmployerId(@PathVariable Long employerId) {
		return jobPostingService.getJobPostingsByEmployerId(employerId);
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
