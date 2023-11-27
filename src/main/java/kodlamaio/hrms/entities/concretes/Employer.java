package kodlamaio.hrms.entities.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kodlamaio.hrms.core.utilities.entities.Role;
import kodlamaio.hrms.core.utilities.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "employers")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "jobPosting" })
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
public class Employer extends User {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "website")
	private String website;

	@JsonIgnore
	@OneToMany(mappedBy = "employer")
	List<JobPosting> jobPostings;

	public Employer(Long id, String email, String password, @NotNull @NotBlank @Size(min = 2) String companyName,
			@NotNull @NotBlank String website, @NotNull @NotBlank @Pattern(regexp = "[0-9\\s]{12}") String phoneNumber,
			List<JobPosting> jobPostings) {
		super.setId(id);
		super.setEmail(email);
		super.setPassword(password);
		this.setCompanyName(companyName);
		this.setWebsite(website);
		this.setPhoneNumber(phoneNumber);
		this.setJobPostings(jobPostings);
	}

	public Employer(String username, String email, String password, String profilePhotoUrl, LocalDateTime verificationExpiry, boolean verified, String companyName, String website,
			String phoneNumber) {
		super(username, email, password, phoneNumber, profilePhotoUrl, verified, verificationExpiry);
		this.companyName = companyName;
		this.website = website;
	}

	public Employer(@NotBlank @Size(max = 20) String username,
			@NotNull @NotBlank @Email(message = "Not a properly formatted email address!") String email,
			@NotNull @NotBlank String password, String phoneNumber, String profilePhotoUrl, Set<Role> roles,
			boolean verified, LocalDateTime verificationExpiry, String companyName, String website,
			List<JobPosting> jobPostings) {
		super(username, email, password, phoneNumber, profilePhotoUrl, roles, verified, verificationExpiry);
		this.companyName = companyName;
		this.website = website;
		this.jobPostings = jobPostings;
	}
	
	

}
