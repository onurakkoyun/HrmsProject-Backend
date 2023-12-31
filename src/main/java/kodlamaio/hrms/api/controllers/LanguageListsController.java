package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.ILanguageListService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.entities.concretes.LanguageList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/language-list")
public class LanguageListsController {
	
	@Autowired
	private ILanguageListService languageListService;
	
	@GetMapping("/getAllLanguageList")
	public DataResult<List<LanguageList>> getAllLanguageList() {
		return this.languageListService.getAllLanguageList();
	}

}
