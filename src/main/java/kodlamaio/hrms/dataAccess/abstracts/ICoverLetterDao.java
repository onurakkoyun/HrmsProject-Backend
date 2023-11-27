package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.CoverLetter;

public interface ICoverLetterDao extends JpaRepository<CoverLetter, Long>{

	List<CoverLetter> findCoverLettersByEmployeeId(Long employeeId);
}
