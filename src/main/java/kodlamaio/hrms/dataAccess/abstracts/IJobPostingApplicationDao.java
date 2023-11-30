package kodlamaio.hrms.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.concretes.JobPostingApplication;

public interface IJobPostingApplicationDao extends JpaRepository<JobPostingApplication, Long>{

	boolean existsByEmployeeAndJobPosting(Employee employee, JobPosting jobPosting);
	
	List<JobPostingApplication> findByJobPosting_JobPostingId(Long jobPostingId);
	
	List<JobPostingApplication> findByResume_ResumeId(Long resumeId);
	
	//List<JobPostingApplication> findByCoverLetter_LetterId(Long letterId);
	
	List<JobPostingApplication> findByEmployee_Id(Long employeeId);
}
