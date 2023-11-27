package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IExperienceService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IExperienceDao;
import kodlamaio.hrms.entities.concretes.Experience;

@Service
public class ExperienceManager implements IExperienceService {

	@Autowired
	private IExperienceDao experienceDao;

	@Override
	public Result add(Experience experience) {
		this.experienceDao.save(experience);
		return new SuccessResult("Experience created successfully.");
	}

	@Override
	public DataResult<List<Experience>> getExperiencesByResumeId(Long resumeId) {
		return new SuccessDataResult<List<Experience>>
		(this.experienceDao.findExperiencesByResumeResumeId(resumeId), "Experiences listed.");
	}

	@Override
	public Result delete(Long experienceId) {
		this.experienceDao.deleteById(experienceId);
		return new SuccessResult("Experience deleted.");
	}

	@Override
	public DataResult<Experience> getExperienceById(Long experienceId) {
		return new SuccessDataResult<Experience>(this.experienceDao.findById(experienceId).orElseThrow(), "Experience listed.");
	}

	@Override
	public Result updateExperience(Experience newExperience) {
		Experience existingExperience = experienceDao.findById(newExperience.getExperienceId()).orElseThrow();
		
		existingExperience.setCompanyName(newExperience.getCompanyName());
		existingExperience.setCompanySector(newExperience.getCompanySector());
		existingExperience.setCityName(newExperience.getCityName());
		existingExperience.setJobDescription(newExperience.getJobDescription());
		existingExperience.setExperienceStart(newExperience.getExperienceStart());
		existingExperience.setExperienceEnd(newExperience.getExperienceEnd());
		existingExperience.setWorkingType(newExperience.getWorkingType());
		existingExperience.setJobTitle(newExperience.getJobTitle());
		
		experienceDao.save(existingExperience);
		return new SuccessResult("Experience updated successfully.");
	}

}
