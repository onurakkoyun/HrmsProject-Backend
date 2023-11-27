package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Employee;
import kodlamaio.hrms.entities.dtos.EmployeesWithDetailsDto;

public interface IEmployeeService {

	public DataResult<List<Employee>> getAll();

	public Result add(Employee employee);
	
	public Result updateEmployee(Employee newEmployee);

	public Result delete(Long id);

	public DataResult<Employee> getEmployeeById(Long employeeId);

	public DataResult<List<EmployeesWithDetailsDto>> getEmployeesWithDetails();

	public boolean checkIfRealPerson(Employee employee);

}
