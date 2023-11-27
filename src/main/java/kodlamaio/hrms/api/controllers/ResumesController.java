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

import kodlamaio.hrms.business.abstracts.IResumeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Resume;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resumes")
public class ResumesController {

	@Autowired
	private IResumeService resumeService;

	@PostMapping(value = "/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public ResponseEntity<?> add(@Valid @RequestBody Resume resume) {
		return ResponseEntity.ok(this.resumeService.add(resume));
	}

	@DeleteMapping("/deleteByResumeId/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.resumeService.delete(id);
	}
	
	@PutMapping("/edit/resumeName/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result updateResumeName(@RequestBody Resume newResume) {
		return resumeService.updateResumeName(newResume);
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result updateResume(@RequestBody Resume newResume) {
		return resumeService.updateResume(newResume);
	}

	@GetMapping("/getallresumes")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public DataResult<List<Resume>> getAllResume() {
		return this.resumeService.getAllResume();
	}

	@GetMapping("/getResumesByEmployeeId/{employeeId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public DataResult<List<Resume>> getResumesByEmployeeId(@PathVariable Long employeeId) {
		return this.resumeService.getResumesByEmployeeId(employeeId);
	}
	
	@GetMapping("/getResumeById/{resumeId}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<Resume> getResumeById(@PathVariable Long resumeId) {
		return resumeService.getResumeById(resumeId);
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
