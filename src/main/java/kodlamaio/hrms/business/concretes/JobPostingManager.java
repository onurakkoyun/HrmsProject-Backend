package kodlamaio.hrms.business.concretes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IJobPostingService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IJobPostingDao;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto;

@Service
public class JobPostingManager implements IJobPostingService {

	private IJobPostingDao jobPostingDao;

	public JobPostingManager(IJobPostingDao jobPostingDao) {
		super();
		this.jobPostingDao = jobPostingDao;
	}

	@Override
	public Result add(JobPosting jobPosting) {
		this.jobPostingDao.save(jobPosting);
		return new SuccessResult("Job posting added");
	}

	@Override
	public Result delete(Long jobPostingId) {
		this.jobPostingDao.deleteById(jobPostingId);
		return new SuccessResult("Job posting deleted");
	}

	@Override
	public DataResult<List<JobPosting>> getAllJobPosting() {
		checkJobPostingsActivity();
		return new SuccessDataResult<List<JobPosting>>(this.jobPostingDao.findAll(), "Job postings listed");
	}

	@Override
	public DataResult<List<JobPostingsWithDetailsDto>> getActiveJobPostingsWithDetails() {
		checkJobPostingsActivity();
		return new SuccessDataResult<List<JobPostingsWithDetailsDto>>(
				this.jobPostingDao.getActiveJobPostingsWithDetails(), "Data listed");
	}

	@Override
	public DataResult<List<JobPostingsWithDetailsDto>> getJobPostingsSortByDate() {
		checkJobPostingsActivity();
		return new SuccessDataResult<List<JobPostingsWithDetailsDto>>(this.jobPostingDao.getJobPostingsSortByDate(),
				"Data listed");
	}

	@Override
	public DataResult<JobPosting> getJobPostingById(Long jobPostingId) {
		checkJobPostingsActivity();
		return new SuccessDataResult<JobPosting>(this.jobPostingDao.findById(jobPostingId).orElseThrow(),
				"Data listed");
	}

	@Override
	public DataResult<List<JobPosting>> getJobPostingsByEmployerId(Long employerId) {
		checkJobPostingsActivity();
		return new SuccessDataResult<List<JobPosting>>(this.jobPostingDao.findByEmployerId(employerId), "Data listed");
	}

	@Override
	public Result updateActivityById(boolean activity, Long jobPostingId) {
		checkJobPostingsActivity();
		this.jobPostingDao.updateActivityById(activity, jobPostingId);
		return new SuccessResult("Job status updated");
	}

	@Override
	public void checkJobPostingsActivity() {
		LocalDate currentDate = LocalDate.now();
		List<JobPosting> jobPostings = jobPostingDao.findAll();
		for (JobPosting jobPosting : jobPostings) {
			LocalDate applicationDeadline = jobPosting.getApplicationDeadline();

			if (applicationDeadline.isBefore(currentDate)) {
				jobPosting.setActive(false);
				jobPostingDao.save(jobPosting);
			}
		}
	}

}
