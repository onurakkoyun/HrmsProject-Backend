package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.CoverLetter;

public interface ICoverLetterService {

	Result add(CoverLetter coverLetter);

	Result delete(Long letterId);
	
	Result updateCoverLetter(CoverLetter newCoverLetter);

	DataResult<List<CoverLetter>> getAllCoverLetters();
	
	DataResult<List<CoverLetter>> getCoverLettersByEmployeeId(@Param("employeeId") Long employeeId);
	
	DataResult<CoverLetter> getLetterById(Long letterId);
}
