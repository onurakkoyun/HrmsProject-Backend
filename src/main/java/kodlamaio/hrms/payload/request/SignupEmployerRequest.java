package kodlamaio.hrms.payload.request;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupEmployerRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	private String profilePhotoUrl;

	private boolean verified;

	private LocalDateTime verificationExpiry;

	@NotNull
	@NotBlank
	@Size(min = 2)
	private String companyName;

	@NotNull
	@NotBlank
	private String website;

	@NotNull
	@NotBlank
	@Pattern(regexp = "[0-9\\s]{11}")
	private String phoneNumber;

	private Set<String> role;

}
