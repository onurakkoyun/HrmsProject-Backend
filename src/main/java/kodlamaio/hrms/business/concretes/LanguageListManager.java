package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.ILanguageListService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.dataAccess.abstracts.ILanguageListDao;
import kodlamaio.hrms.entities.concretes.LanguageList;

@Service
public class LanguageListManager implements ILanguageListService{
	
	@Autowired
	private ILanguageListDao languageListDao;

	@Override
	public DataResult<List<LanguageList>> getAllLanguageList() {
		return new SuccessDataResult<List<LanguageList>>(this.languageListDao.findAll());
	}

}
