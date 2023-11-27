package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Admin;

public interface IAdminService {

	DataResult<List<Admin>> getAll();

	Result add(Admin systemStuff);

	Result delete(int id);
}
