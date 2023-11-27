package kodlamaio.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Skill;

public interface ISkillService {

	Result add(Skill skill);

	Result delete(int skillId);

	DataResult<Skill> getSkillById(int skillId);

	Result updateSkill(Skill newSkill);

	DataResult<List<Skill>> getSkillsByResumeId(@Param("resumeId") Long resumeId);

}
