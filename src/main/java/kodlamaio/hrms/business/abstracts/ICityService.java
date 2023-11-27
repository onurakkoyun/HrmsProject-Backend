package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.City;


public interface ICityService {
	
	public DataResult<List<City>> getAll();

	public Result add(City city);

	public Result delete(int id);

}
