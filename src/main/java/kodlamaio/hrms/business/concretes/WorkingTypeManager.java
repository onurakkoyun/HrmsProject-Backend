package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IWorkingTypeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.dataAccess.abstracts.IWorkingTypeDao;
import kodlamaio.hrms.entities.concretes.WorkingType;

@Service
public class WorkingTypeManager implements IWorkingTypeService {
	
	@Autowired
	private IWorkingTypeDao workingTypeDao;

	@Override
	public DataResult<List<WorkingType>> getAllWorkingType() {
		return new SuccessDataResult<List<WorkingType>>(this.workingTypeDao.findAll(), "Working types listed.");
	}

}
