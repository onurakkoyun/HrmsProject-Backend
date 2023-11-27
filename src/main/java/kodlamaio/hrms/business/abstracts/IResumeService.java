package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Resume;

public interface IResumeService {
	Result add(Resume resume);

	Result delete(Long resumeId);
	
	Result updateResume(Resume newResume);
	
	Result updateResumeName(Resume newResume);

	DataResult<List<Resume>> getAllResume();
	
	DataResult<List<Resume>> getResumesByEmployeeId(@Param("employeeId") Long employeeId);
	
	DataResult<Resume> getResumeById(Long resumeId);

	// DataResult<List<ResumesWithDetailsDto>>
	// getResumesWithDetails(@Param("resumeId") int resumeId);

}
