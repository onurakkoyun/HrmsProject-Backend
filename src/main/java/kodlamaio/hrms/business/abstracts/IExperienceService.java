package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Experience;

public interface IExperienceService {

	Result add(Experience experience);
	
	Result delete(Long experienceId);
	
	DataResult<Experience> getExperienceById(Long experienceId);
	
	Result updateExperience(Experience newExperience);

	DataResult<List<Experience>> getExperiencesByResumeId(@Param("resumeId") Long resumeId);
}
