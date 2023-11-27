package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.Language;

public interface ILanguageDao extends JpaRepository<Language, Integer>{
	
	List<Language> findLanguagesByResumeResumeId(Long resumeId);
}
