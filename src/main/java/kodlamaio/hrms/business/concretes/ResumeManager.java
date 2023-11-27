package kodlamaio.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IResumeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IJobPostingApplicationDao;
import kodlamaio.hrms.dataAccess.abstracts.IResumeDao;
import kodlamaio.hrms.entities.concretes.JobPostingApplication;
import kodlamaio.hrms.entities.concretes.Resume;

@Service
public class ResumeManager implements IResumeService {

	@Autowired
	private IResumeDao resumeDao;
	
	@Autowired
	private IJobPostingApplicationDao applicationDao;

	@Override
	public Result add(Resume resume) {
		this.resumeDao.save(resume);
		return new SuccessResult("Resume added");
	}

	@Override
	public Result delete(Long resumeId) {
		List<JobPostingApplication> applications = applicationDao.findByResume_ResumeId(resumeId);
	    
	    for (JobPostingApplication application : applications) {
	    	applicationDao.deleteById(application.getApplicationId());
	       /* application.setResume(null);
	        applicationDao.save(application);*/
	    }

	    this.resumeDao.deleteById(resumeId);
	    return new SuccessResult("Resume deleted.");
	}

	@Override
	public DataResult<List<Resume>> getAllResume() {
		return new SuccessDataResult<List<Resume>>(this.resumeDao.findAll());
	}

	@Override
	public DataResult<List<Resume>> getResumesByEmployeeId(Long employeeId) {
		return new SuccessDataResult<List<Resume>>(this.resumeDao.findResumesByEmployeeId(employeeId),
				"Resumes listed.");
	}

	@Override
	public DataResult<Resume> getResumeById(Long resumeId) {
		Optional<Resume> optionalResume = resumeDao.findById(resumeId);
		
		if (optionalResume.isPresent()) {
		    Resume existingResume = optionalResume.get();
		    return new SuccessDataResult<Resume>(existingResume, "Resume listed.");
		} else {
			return new ErrorDataResult<>("No resume found resumeId:"+resumeId);
		}
	}

	@Override
	public Result updateResumeName(Resume newResume) {
		Resume existingResume = resumeDao.findById(newResume.getResumeId()).orElseThrow();

		existingResume.setResumeName(newResume.getResumeName());
		resumeDao.save(existingResume);
		
		return new SuccessResult("Resume name updated successfully.");
	}

	@Override
	public Result updateResume(Resume newResume) {
		Resume existingResume = resumeDao.findById(newResume.getResumeId()).orElseThrow();

		existingResume.setJobTitle(newResume.getJobTitle());
		existingResume.setGender(newResume.getGender());
		existingResume.setDrivingLicence(newResume.getDrivingLicence());
		existingResume.setMilitaryStatus(newResume.getMilitaryStatus());
		if (newResume.getMilitaryStatus().equals("Postponed")) {
			existingResume.setPostponedDate(newResume.getPostponedDate());
		}
		else {
			existingResume.setPostponedDate(null);
		}
		existingResume.setGithubAddress(newResume.getGithubAddress());
		existingResume.setLinkedinAddress(newResume.getLinkedinAddress());
		existingResume.setPersonalWebsite(newResume.getPersonalWebsite());
		
		resumeDao.save(existingResume);
		return new SuccessResult("Resume updated successfully.");
	}

}
