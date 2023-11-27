package kodlamaio.hrms.dataAccess.abstracts;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.dtos.EmployersWithDetailsDto;

public interface IEmployerDao extends JpaRepository<Employer, Long>{
		
	@Query("Select new kodlamaio.hrms.entities.dtos.EmployersWithDetailsDto(e.companyName, e.website, e.email) "
			+ "From Employer e ")
	List<EmployersWithDetailsDto> getEmployersWithDetails();

}
