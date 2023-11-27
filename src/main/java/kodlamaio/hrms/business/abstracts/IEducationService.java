package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Education;

public interface IEducationService {
	Result add(Education education);

	Result delete(Long educationId);
	
	DataResult<Education> getEducationById(Long educationId);
	
	Result updateEducation(Education newEducation);

	DataResult<List<Education>> getEducationsByResumeId(@Param("resumeId") Long resumeId);
}
