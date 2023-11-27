package kodlamaio.hrms.entities.dtos;


import java.util.List;

import kodlamaio.hrms.entities.concretes.Experience;
import kodlamaio.hrms.entities.concretes.Language;
import kodlamaio.hrms.entities.concretes.Education;
import kodlamaio.hrms.entities.concretes.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumesWithDetailsDto {
	/*private String firstName;
	private String lastName;
	private String email;
	private String coverLetter;
	private String githubAddress;
	private String linkedinAddress;
	private String schoolName;
	private String department;
	//private Date schoolStart;
	//private Date schoolEnd;
	private boolean isContinue;
	private String languageName;
	private int languageLevel;
	private String companyName;
	private String positionName;
	private String skillName;
	//private Date experienceStart;
	//private Date experienceEnd;*/
	
	private String firstName;
	private String lastName;
	private String email;
	private String coverLetter;
	private String githubAddress;
	private String linkedinAddress;
	
	private List<Experience> experiences;
	private List<Language> languages;
	private List<Education> educations;
	private List<Skill> skills;
	

}
