package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import kodlamaio.hrms.business.abstracts.IJobPostingApplicationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.ICoverLetterDao;
import kodlamaio.hrms.dataAccess.abstracts.IJobPostingApplicationDao;
import kodlamaio.hrms.dataAccess.abstracts.IResumeDao;
import kodlamaio.hrms.entities.concretes.CoverLetter;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.concretes.JobPostingApplication;
import kodlamaio.hrms.entities.concretes.Resume;

@Service
public class JobPostingApplicationManager implements IJobPostingApplicationService {

	@Autowired
	private IJobPostingApplicationDao applicationDao;

	@Autowired
	private ICoverLetterDao letterDao;

	@Autowired
	private IResumeDao resumeDao;

	@Override
	public Result add(@RequestBody JobPostingApplication jobPostingApplication) {
		Employee employee = jobPostingApplication.getEmployee();
		JobPosting jobPosting = jobPostingApplication.getJobPosting();

		if (isEmployeeAlreadyAppliedToJobPosting(employee, jobPosting)) {
			return new ErrorResult("You already applied to this job posting.");
		}

		Resume resume = resumeDao.findById(jobPostingApplication.getResume().getResumeId()).orElseThrow();
		jobPostingApplication.setResume(resume);

		CoverLetter coverLetter = letterDao.findById(jobPostingApplication.getCoverLetter().getLetterId())
				.orElseThrow();
		jobPostingApplication.setCoverLetter(coverLetter);

		this.applicationDao.save(jobPostingApplication);
		return new SuccessResult("Application successful.");
	}

	@Override
	public DataResult<List<JobPostingApplication>> getAll() {
		return new SuccessDataResult<List<JobPostingApplication>>(this.applicationDao.findAll());
	}

	@Override
	public DataResult<JobPostingApplication> getApplicationById(Long applicationId) {
		return new SuccessDataResult<JobPostingApplication>(this.applicationDao.findById(applicationId).orElseThrow());
	}

	@Override
	public Result delete(Long applicationId) {
		this.applicationDao.deleteById(applicationId);
		return new SuccessResult("Application deleted.");
	}

	@Override
	public boolean isEmployeeAlreadyAppliedToJobPosting(Employee employee, JobPosting jobPosting) {
		return applicationDao.existsByEmployeeAndJobPosting(employee, jobPosting);
	};

	@Override
	public DataResult<List<JobPostingApplication>> findApplicationsByEmployeeId(Long employeeId) {
		return new SuccessDataResult<List<JobPostingApplication>>(this.applicationDao.findByEmployee_Id(employeeId),
				"Applications listed.");
	}

	@Override
	public DataResult<List<JobPostingApplication>> findApplicationsByJobPostingId(Long jobPostingId) {
		return new SuccessDataResult<List<JobPostingApplication>>(
				this.applicationDao.findByJobPosting_JobPostingId(jobPostingId), "Applications listed.");
	}

}
