package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Language;

public interface ILanguageService {

	Result add(Language language);

	Result delete(int languageId);
	
	DataResult<Language> getLanguageById(int languageId);
	
	Result updateLanguage(Language newLanguage);

	DataResult<List<Language>> getLanguagesByResumeId(@Param("resumeId") Long resumeId);

}
