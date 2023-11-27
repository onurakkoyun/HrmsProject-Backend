package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.dtos.EmployeesWithDetailsDto;

public interface IEmployeeDao extends JpaRepository<Employee, Long>{

	boolean existsEmployeeByEmail(String email);
	boolean existsEmployeeByIdentityNumber(String identityNumber);
	
	Employee findByEmail(String email);
	
	@Query("Select new kodlamaio.hrms.entities.dtos.EmployeesWithDetailsDto(e.firstName, e.lastName, e.email, e.yearOfBirth) "
			+ "From Employee e")
	List<EmployeesWithDetailsDto> getEmployeesWithDetails();

}
