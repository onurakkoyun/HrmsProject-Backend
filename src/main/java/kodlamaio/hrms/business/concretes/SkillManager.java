package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.ISkillService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.ISkillDao;
import kodlamaio.hrms.entities.concretes.Skill;

@Service
public class SkillManager implements ISkillService{

	@Autowired
	private ISkillDao skillDao;

	@Override
	public Result add(Skill skill) {
		this.skillDao.save(skill);
		return new SuccessResult("Skill added successfully.");
	}

	@Override
	public Result delete(int skillId) {
		this.skillDao.deleteById(skillId);
		return new SuccessResult("Skill deleted.");
	}

	@Override
	public DataResult<Skill> getSkillById(int skillId) {
		return new SuccessDataResult<Skill>(skillDao.findById(skillId).orElseThrow(), "Skill listed.");
	}

	@Override
	public Result updateSkill(Skill newSkill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<Skill>> getSkillsByResumeId(Long resumeId) {
		return new SuccessDataResult<List<Skill>>
		(this.skillDao.findSkillsByResumeResumeId(resumeId), "Skills listed.");
	}

}
