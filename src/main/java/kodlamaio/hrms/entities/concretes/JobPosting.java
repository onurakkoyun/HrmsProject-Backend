package kodlamaio.hrms.entities.concretes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "job_postings")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "jobPostingApplication"}) 
public class JobPosting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_posting_id")
	private Long jobPostingId;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id")
	JobTitle jobTitle;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	City city;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")	
	Employer employer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="working_type_id")	
	WorkingType workingType;
	
	@JsonIgnore
	@OneToMany(mappedBy = "jobPosting", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<JobPostingApplication> jobPostingApplications;
	
	@PastOrPresent
	@CreationTimestamp
	@Column(name = "publication_date", nullable = false, updatable = false)
    private LocalDateTime publicationDate;
	
	@NotNull
	@Future(message = "Son başvuru tarihi geçmiş bir zaman olamaz!")
	@Column(name = "application_deadline")
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate applicationDeadline;
	
	@Column(name = "salary_min")
	private String salaryMin;
	
	@Column(name = "salary_max")
	private String salaryMax;
	
	@NotNull
	@Min(1)
	@Column(name = "available_position")
	private int availablePosition;

	@NotNull
	@NotBlank
	@Column(name = "job_description")
	private String jobDescription;
	
	@Column(name = "job_summary")
	private String jobSummary;
	
	@Column(name = "is_active")
	private boolean isActive;
}
