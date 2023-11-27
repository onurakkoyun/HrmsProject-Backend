package kodlamaio.hrms.entities.concretes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "resumes")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resume_id")
	private Long resumeId;
	
	@Column(name = "resume_name")
	private String resumeName;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "driving_licence")
	private String drivingLicence;

	@Column(name = "military_status")
	private String militaryStatus;
	
	@Column(name = "postponed_date")
	private LocalDate postponedDate;

	@Column(name = "github_address")
	private String githubAddress;

	@Column(name = "linkedin_address")
	private String linkedinAddress;
	
	@Column(name = "personal_website")
	private String personalWebsite;
	
	@PastOrPresent
	@CreationTimestamp
	@Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	Employee employee;

	@JsonIgnore
	@OneToMany(mappedBy = "resume")
	List<JobPostingApplication> jobPostingApplications;

	@JsonIgnore
	@OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
	List<Experience> experiences;

	@JsonIgnore
	@OneToMany(mappedBy = "resume")
	List<Language> languages;

	@JsonIgnore
	@OneToMany(mappedBy = "resume")
	List<Education> educations;

	@JsonIgnore
	@OneToMany(mappedBy = "resume")
	List<Skill> skills;
	
}
