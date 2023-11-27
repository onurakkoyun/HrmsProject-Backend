package kodlamaio.hrms.core.utilities.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "userName", unique = true)
	private String username;

	@NotNull
	@NotBlank
	@Email(message = "Not a properly formatted email address!")
	@Column(name = "email")
	private String email;

	@NotNull
	@NotBlank
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "profile_photo_url")
	private String profilePhotoUrl;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"), 
	inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<>();

	private boolean verified;

	private LocalDateTime verificationExpiry;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(@NotBlank @Size(max = 20) String username,
			@NotNull @NotBlank @Email(message = "Not a properly formatted email address!") String email,
			@NotNull @NotBlank String password, boolean verified, LocalDateTime verificationExpiry) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.verified = verified;
		this.verificationExpiry = verificationExpiry;
	}	
	
	public User(@NotBlank @Size(max = 20) String username,
			@NotNull @NotBlank @Email(message = "Not a properly formatted email address!") String email,
			@NotNull @NotBlank String password, String profilePhotoUrl, boolean verified, LocalDateTime verificationExpiry) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.profilePhotoUrl = profilePhotoUrl;
		this.verified = verified;
		this.verificationExpiry = verificationExpiry;
	}

	public User(@NotBlank @Size(max = 20) String username,
			@NotNull @NotBlank @Email(message = "Not a properly formatted email address!") String email,
			@NotNull @NotBlank String password, String phoneNumber, String profilePhotoUrl, Set<Role> roles,
			boolean verified, LocalDateTime verificationExpiry) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.profilePhotoUrl = profilePhotoUrl;
		this.roles = roles;
		this.verified = verified;
		this.verificationExpiry = verificationExpiry;
	}	
	
	public User(@NotBlank @Size(max = 20) String username,
			@NotNull @NotBlank @Email(message = "Not a properly formatted email address!") String email,
			@NotNull @NotBlank String password, String phoneNumber, String profilePhotoUrl,
			boolean verified, LocalDateTime verificationExpiry) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.profilePhotoUrl = profilePhotoUrl;
		this.verified = verified;
		this.verificationExpiry = verificationExpiry;
	}	
	
	

}
