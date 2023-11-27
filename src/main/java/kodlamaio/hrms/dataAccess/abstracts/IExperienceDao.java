package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Experience;

public interface IExperienceDao extends JpaRepository<Experience, Long> {
	 List<Experience> findExperiencesByResumeResumeId(Long resumeId);
}
