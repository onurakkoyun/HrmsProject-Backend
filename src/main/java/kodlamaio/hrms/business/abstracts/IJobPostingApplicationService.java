package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.concretes.JobPostingApplication;

public interface IJobPostingApplicationService {

	DataResult<List<JobPostingApplication>> getAll();

	Result add(JobPostingApplication jobPostingApplication);

	Result delete(Long applicationId);
	
	DataResult<JobPostingApplication> getApplicationById(Long applicationId);
	
	DataResult<List<JobPostingApplication>> findApplicationsByEmployeeId(Long employeeId);
	
	DataResult<List<JobPostingApplication>> findApplicationsByJobPostingId(Long jobPostingId);
	
	boolean isEmployeeAlreadyAppliedToJobPosting(Employee employee, JobPosting jobPosting);
}
