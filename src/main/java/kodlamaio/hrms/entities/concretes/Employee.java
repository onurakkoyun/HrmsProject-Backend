package kodlamaio.hrms.entities.concretes;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kodlamaio.hrms.core.utilities.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "employee_id", referencedColumnName = "id")
@Table(name = "employees")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Employee extends User {

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "identity_number", unique = true)
	private String identityNumber;

	@Column(name = "year_of_birth")
	private int yearOfBirth;

	@Column(name = "country")
	private String country;

	@Column(name = "province")
	private String province;

	@Column(name = "city")
	private String city;

	@Column(name = "address")
	private String address;

	@Column(name = "postal_code")
	private String postalCode;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	List<Resume> resumes;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	List<JobPostingApplication> jobPostingApplications;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	List<CoverLetter> coverLetters;

	public Employee(Long id, String username, String email, String password,
			@NotNull @NotBlank(message = "Ad boş olamaz") @Size(min = 2, message = "Ad en az 2 haneli olmalıdır") String firstName,
			@NotNull @NotBlank(message = "Soyad boş olamaz") @Size(min = 2, message = "Soyad en az 2 haneli olmalıdır") String lastName,
			@NotNull @NotBlank(message = "Kimlik numarası boş olamaz") @Size(min = 11, max = 11, message = "Kimlik numarası 11 haneli olmalıdır") String identityNumber,
			int yearOfBirth, List<JobPostingApplication> jobPostingApplications, List<Resume> resumes,
			List<CoverLetter> coverLetters) {
		super.setId(id);
		super.setUsername(username);
		super.setEmail(email);
		super.setPassword(password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setIdentityNumber(identityNumber);
		this.setYearOfBirth(yearOfBirth);
		this.setJobPostingApplications(jobPostingApplications);
		this.setResumes(resumes);
		this.setCoverLetters(coverLetters);
	}

	public Employee(String username, String email, String password, String profilePhotoUrl,
			LocalDateTime verificationExpiry, boolean verified, String firstName, String lastName,
			String identityNumber, int yearOfBirth) {
		super(username, email, password, profilePhotoUrl, verified, verificationExpiry);
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.yearOfBirth = yearOfBirth;
	}

	public Employee(String username, String email, String password, String firstName, String lastName, String country,
			String province, String city, String address, String postalCode) {
		super(username, email, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.province = province;
		this.city = city;
		this.address = address;
		this.postalCode = postalCode;
	}

}
