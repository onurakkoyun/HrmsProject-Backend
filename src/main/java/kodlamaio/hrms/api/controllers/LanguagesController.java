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

import kodlamaio.hrms.business.abstracts.ILanguageService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Language;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/languages")
public class LanguagesController {

	@Autowired
	private ILanguageService languageService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @RequestBody Language language){
		return ResponseEntity.ok(this.languageService.add(language));
	}
	
	@GetMapping("/getLanguagesByResumeId/{resumeId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public DataResult<List<Language>> getLanguagesByResumeId(@PathVariable Long resumeId) {
		return this.languageService.getLanguagesByResumeId(resumeId);
	}
	
	@GetMapping("/getLanguageById/{languageId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public DataResult<Language> getLanguageById(@PathVariable int languageId) {
		return languageService.getLanguageById(languageId);
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result updateLanguage(@RequestBody Language newLanguage) {
		return languageService.updateLanguage(newLanguage);
	}

	@DeleteMapping("/deleteByLanguageId/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	public Result deleteById(@PathVariable("id") int id) {
		return this.languageService.delete(id);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String, String> validationErrors = new HashMap<String, String>();
		
		for (FieldError fieldError: exceptions.getBindingResult().getFieldErrors() ) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama Hataları");
		return errors;
	}

}
