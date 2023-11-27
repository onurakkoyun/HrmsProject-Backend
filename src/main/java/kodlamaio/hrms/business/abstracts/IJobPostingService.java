package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto;

public interface IJobPostingService {

	Result add(JobPosting jobPosting);
	
	Result delete(Long jobPostingId);
	
	DataResult<List<JobPosting>> getAllJobPosting();

	DataResult<List<JobPostingsWithDetailsDto>> getActiveJobPostingsWithDetails();
	
	Result updateActivityById(@Param("isActive") boolean activity,@Param("jobPostingId") Long jobPostingId);
	
	DataResult<List<JobPostingsWithDetailsDto>> getJobPostingsSortByDate();
	
	DataResult<JobPosting> getJobPostingById(@Param("jobPostingId") Long jobPostingId);
	
	DataResult<List<JobPosting>> getJobPostingsByEmployerId(@Param("employerId") Long employerId);
	
	void checkJobPostingsActivity();
	
	//public boolean existsJobTitlesByJobTitle(String jobTitle);
	
	
	
}
