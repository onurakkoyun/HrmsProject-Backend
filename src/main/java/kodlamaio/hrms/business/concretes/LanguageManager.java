package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.ILanguageService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.ILanguageDao;
import kodlamaio.hrms.entities.concretes.Language;

@Service
public class LanguageManager implements ILanguageService{

	@Autowired
	private ILanguageDao languageDao;

	@Override
	public Result add(Language language) {
		this.languageDao.save(language);
		return new SuccessResult("Language created successfully.");
	}

	@Override
	public Result delete(int languageId) {
		this.languageDao.deleteById(languageId);
		return new SuccessResult("Language deleted.");
	}


	@Override
	public DataResult<Language> getLanguageById(int languageId) {
		return new SuccessDataResult<Language>(this.languageDao.findById(languageId).orElseThrow(), "Language listed.");
	}

	@Override
	public Result updateLanguage(Language newLanguage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<Language>> getLanguagesByResumeId(Long resumeId) {
		return new SuccessDataResult<List<Language>>
		(this.languageDao.findLanguagesByResumeResumeId(resumeId), "Languages listed.");
	}

}
