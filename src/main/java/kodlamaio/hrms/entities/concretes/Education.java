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

@Data
@Entity
@Table(name = "educations")
@AllArgsConstructor
@NoArgsConstructor
public class Education {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "education_id")
	private Long educationId;
	
	@Column(name = "education_level")
	private String educationLevel;
	
	@Column(name = "university_name")
	private String universityName;
	
	@Column(name = "faculty")
	private String faculty;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "city_name")
	private String cityName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "degree_type")
	private double degreeType;
	
	@Column(name = "graduation_degree")
	private double graduationDegree;
	
	@Column(name = "education_type")
	private String educationType;
	
	@Column(name = "education_language")
	private String educationLanguage;
	
	@Past
	@Column(name = "starting_date")
	private Date startingDate;
	
	@Past
	@Column(name = "ending_date")
	private Date endingDate;
	
	@Column(name = "is_continue")
	private boolean isContinue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id")
	Resume resume;
}
