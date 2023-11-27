package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.IEducationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IEducationDao;
import kodlamaio.hrms.entities.concretes.Education;

@Service
public class EducationManager implements IEducationService{

	@Autowired
	private IEducationDao educationDao;

	@Override
	public Result add(Education education) {
		this.educationDao.save(education);
		return new SuccessResult("Education created successfully.");
	}

	@Override
	public Result delete(Long educationId) {
		this.educationDao.deleteById(educationId);
		return new SuccessResult("Education deleted.");
	}

	@Override
	public DataResult<List<Education>> getEducationsByResumeId(Long resumeId) {
		return new SuccessDataResult<List<Education>>
		(this.educationDao.findEducationsByResumeResumeId(resumeId), "Educations listed.");
	}

	@Override
	public Result updateEducation(Education newEducation) {
		Education existingEducation = educationDao.findById(newEducation.getEducationId()).orElseThrow();
		
		existingEducation.setEducationLevel(newEducation.getEducationLevel());
		existingEducation.setEducationType(newEducation.getEducationType());
		existingEducation.setEducationLevel(newEducation.getEducationLevel());
		existingEducation.setEducationLanguage(newEducation.getEducationLanguage());
		existingEducation.setStartingDate(newEducation.getStartingDate());
		existingEducation.setEndingDate(newEducation.getEndingDate());
		existingEducation.setContinue(newEducation.isContinue());
		existingEducation.setDegreeType(newEducation.getDegreeType());
		existingEducation.setGraduationDegree(newEducation.getGraduationDegree());
		existingEducation.setEducationLevel(newEducation.getEducationLevel());
		existingEducation.setUniversityName(newEducation.getUniversityName());
		existingEducation.setFaculty(newEducation.getFaculty());
		existingEducation.setDepartment(newEducation.getDepartment());
		existingEducation.setCityName(newEducation.getCityName());
		existingEducation.setDescription(newEducation.getDescription());
		
		educationDao.save(existingEducation);
		return new SuccessResult("Education updated successfully.");
	}

	@Override
	public DataResult<Education> getEducationById(Long educationId) {
		return new SuccessDataResult<Education>(this.educationDao.findById(educationId).orElseThrow(), "Education listed.");
	}

}
