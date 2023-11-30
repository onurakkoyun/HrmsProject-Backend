package kodlamaio.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.ICoverLetterService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.ICoverLetterDao;
import kodlamaio.hrms.entities.concretes.CoverLetter;

@Service
public class CoverLetterManager implements ICoverLetterService {

	@Autowired
	private ICoverLetterDao coverLetterDao;

	@Override
	public Result add(CoverLetter coverLetter) {
		this.coverLetterDao.save(coverLetter);
		return new SuccessDataResult<>("Cover letter added.");
	}

	@Override
	public Result delete(Long letterId) {
		this.coverLetterDao.deleteById(letterId);
		return new SuccessResult("Letter deleted.");
	}
	
	@Override
	public Result updateCoverLetter(CoverLetter newCoverLetter) {
		CoverLetter existingLetter = coverLetterDao.findById(newCoverLetter.getLetterId()).orElseThrow();

		existingLetter.setLetterName(newCoverLetter.getLetterName());
		existingLetter.setLetterContent(newCoverLetter.getLetterContent());
		
		coverLetterDao.save(existingLetter);
		return new SuccessResult("Cover letter updated successfully.");
	}

	@Override
	public DataResult<List<CoverLetter>> getAllCoverLetters() {
		return new SuccessDataResult<List<CoverLetter>>(this.coverLetterDao.findAll());
	}

	@Override
	public DataResult<List<CoverLetter>> getCoverLettersByEmployeeId(Long employeeId) {
		return new SuccessDataResult<List<CoverLetter>>(this.coverLetterDao.findCoverLettersByEmployeeId(employeeId),
				"CoverLetters listed.");
	}

	@Override
	public DataResult<CoverLetter> getLetterById(Long letterId) {
		Optional<CoverLetter> optionalLetter = coverLetterDao.findById(letterId);

		if (optionalLetter.isPresent()) {
			CoverLetter existingLetter = optionalLetter.get();
			return new SuccessDataResult<CoverLetter>(existingLetter, "Cover letter listed.");
		} else {
			return new ErrorDataResult<>("No letter found letterId:" + letterId);
		}
	}


}
