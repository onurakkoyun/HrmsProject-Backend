package kodlamaio.hrms.entities.concretes;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experiences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "experience_id")
	private Long experienceId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_sector")
	private String companySector;
	
	@Column(name = "city_name")
	private String cityName;
	
	@Column(name = "job_description")
	private String jobDescription;
	
	@Past
	@Column(name = "start_date")
	private Date experienceStart;
	
	@Column(name = "end_date")
	private Date experienceEnd;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id")
	Resume resume;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="working_type_id")	
	WorkingType workingType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="title_id")	
	JobTitle jobTitle;
	
	
	

}
