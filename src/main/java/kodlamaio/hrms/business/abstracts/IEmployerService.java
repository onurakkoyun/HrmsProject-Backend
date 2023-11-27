package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.dtos.EmployersWithDetailsDto;

public interface IEmployerService {

	public DataResult<List<Employer>> getAll();

	public Result add(Employer employer);
	
	public Result updateEmployer(Employer newEmployer);

	public Result delete(Long id);
	
	public DataResult<Employer> getEmployerById(Long employerId);

	public DataResult<List<EmployersWithDetailsDto>> getEmployersWithDetails();

}
