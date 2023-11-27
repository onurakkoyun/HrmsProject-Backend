package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import kodlamaio.hrms.entities.concretes.Resume;

public interface IResumeDao extends JpaRepository<Resume, Long>{
	
	List<Resume> findResumesByEmployeeId(Long employeeId);
	
	/*@Query("SELECT new kodlamaio.hrms.entities.dtos.ResumesWithDetailsDto"
			+ "(em.firstName, em.lastName, em.email, rs.coverLetter, rs.githubAddress, rs.linkedinAddress, sc.schoolName, sc.department, sc.isContinue, lg.languageName, lg.languageLevel, ex.companyName, ex.positionName, sk.skillName)"
			+ " FROM Resume rs JOIN Employee em ON rs.employee.id = em.id"
			+ " JOIN rs.schools sc JOIN rs.languages lg JOIN rs.experiences ex"
			+ " JOIN rs.skills sk")
	List<ResumesWithDetailsDto> getResumesWithDetails();

	@Query("SELECT rs FROM Resume rs JOIN Employee em ON rs.employee.id = em.id"
			+ " JOIN FETCH rs.schools sc JOIN FETCH rs.languages lg JOIN FETCH rs.experiences ex"
			+ " JOIN FETCH rs.skills sk WHERE rs.resumeId = ?1")
	List<ResumesWithDetailsDto> getResumesWithDetails(@Param("resumeId") int resumeId);*/
}
