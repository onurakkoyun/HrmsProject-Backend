package kodlamaio.hrms.payload.request;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupEmployeeRequest {

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
	@NotBlank(message = "Ad boş olamaz")
	@Size(min = 2, message = "Ad en az 2 haneli olmalıdır")
	private String firstName;

	@NotNull
	@NotBlank(message = "Soyad boş olamaz")
	@Size(min = 2, message = "Soyad en az 2 haneli olmalıdır")
	private String lastName;

	@NotNull
	@NotBlank(message = "Kimlik numarası boş olamaz")
	@Size(min = 11, max = 11, message = "Kimlik numarası 11 haneli olmalıdır")
	private String identityNumber;

	private int yearOfBirth;

	private Set<String> role;
}
