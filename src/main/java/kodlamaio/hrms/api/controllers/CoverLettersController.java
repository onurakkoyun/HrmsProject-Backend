package kodlamaio.hrms.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.ICoverLetterService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.CoverLetter;

@RestController
@RequestMapping("/api/coverLetter")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CoverLettersController {

	@Autowired
	private ICoverLetterService coverLetterService;

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @RequestBody CoverLetter coverLetter) {
		return ResponseEntity.ok(this.coverLetterService.add(coverLetter));
	}

	@DeleteMapping("/deleteByCoverLetterId/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result deleteById(@PathVariable("id") Long id) {
		return this.coverLetterService.delete(id);
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result updateCoverLetter(@RequestBody CoverLetter newCoverLetter) {
		return coverLetterService.updateCoverLetter(newCoverLetter);
	}

	@GetMapping("/getAllCoverLetters")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public DataResult<List<CoverLetter>> getAllCoverLetters() {
		return this.coverLetterService.getAllCoverLetters();
	}
	
	@GetMapping("/getCoverLetterById/{letterId}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('EMPLOYER')")
	public DataResult<CoverLetter> getLetterById(@PathVariable Long letterId) {
		return coverLetterService.getLetterById(letterId);
	}
	
	@GetMapping("/getCoverLettersByEmployeeId/{employeeId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public DataResult<List<CoverLetter>> getCoverLettersByEmployeeId(@PathVariable Long employeeId) {
		return this.coverLetterService.getCoverLettersByEmployeeId(employeeId);
	}
}
