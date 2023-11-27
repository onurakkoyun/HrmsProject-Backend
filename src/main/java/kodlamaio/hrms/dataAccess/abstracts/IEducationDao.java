package kodlamaio.hrms.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Education;

public interface IEducationDao extends JpaRepository<Education, Long>{
	List<Education> findEducationsByResumeResumeId(Long resumeId);
}
