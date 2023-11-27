package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.entities.concretes.JobPosting;
import kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto;

public interface IJobPostingDao extends JpaRepository<JobPosting, Long> {

	@Query("Select new kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto(jp.jobPostingId, e.id, e.companyName, jt.titleId, jt.jobTitleName, ct.cityId, ct.cityName, jp.publicationDate, jp.applicationDeadline, jp.availablePosition, jp.jobSummary, jp.jobDescription) "
			+ "From JobPosting jp join JobTitle jt on jp.jobTitle.titleId = jt.titleId "
			+ "join Employer e on jp.employer.id = e.id join "
			+ "City ct on jp.city.cityId = ct.cityId WHERE jp.isActive = true")
	List<JobPostingsWithDetailsDto> getActiveJobPostingsWithDetails();

	@Query("Select new kodlamaio.hrms.entities.dtos.JobPostingsWithDetailsDto(jp.jobPostingId, e.id, e.companyName, jt.titleId, jt.jobTitleName, ct.cityId, ct.cityName, jp.publicationDate, jp.applicationDeadline, jp.availablePosition, jp.jobSummary, jp.jobDescription) "
			+ "From JobPosting jp join JobTitle jt on jp.jobTitle.titleId = jt.titleId "
			+ "join Employer e on jp.employer.id = e.id join "
			+ "City ct on jp.city.cityId = ct.cityId WHERE jp.isActive = true Order By jp.publicationDate DESC ")
	List<JobPostingsWithDetailsDto> getJobPostingsSortByDate();
	
	List<JobPosting> findByEmployerId(Long employerId);

	@Transactional
	@Modifying
	@Query("UPDATE JobPosting SET isActive=:isActive WHERE jobPostingId=:jobPostingId")
	void updateActivityById(@Param("isActive") boolean activity, @Param("jobPostingId") Long jobPostingId);

}
