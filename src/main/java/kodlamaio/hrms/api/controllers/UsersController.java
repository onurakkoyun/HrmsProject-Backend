package kodlamaio.hrms.api.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import com.google.cloud.storage.Blob;
import com.google.cloud.ReadChannel;

import kodlamaio.hrms.business.abstracts.IUserService;
import kodlamaio.hrms.core.utilities.entities.User;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsersController {

	@Autowired
	private IUserService userService;

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMIN')")
	public DataResult<List<User>> getAll() {
		return this.userService.getAll();
	}

	@GetMapping("/email")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public DataResult<User> getByEmail(@RequestParam String email) {
		return this.userService.getByEmail(email);
	}

	@PostMapping("/{userId}/profile-image")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				long fileSize = file.getSize();
				String contentType = file.getContentType();
				userService.uploadProfileImage(userId, file.getInputStream(), fileSize, contentType);
				return new ResponseEntity<>("Profile photo uploaded successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("The uploaded file is empty or incorrect", HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			return new ResponseEntity<>("Profile photo upload error: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{userId}/get-profile-image")
	public ResponseEntity<byte[]> getProfileImage(@PathVariable Long userId, HttpServletResponse response) {
		// Kullanıcının profil fotoğrafını aldık
		Blob imageBlob = userService.getImageBlob(userId.toString());
		if (imageBlob != null) {
			try (ReadChannel readChannel = imageBlob.reader()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = readChannel.read(ByteBuffer.wrap(buffer))) != -1) {
					baos.write(buffer, 0, bytesRead);
				}
				byte[] imageBytes = baos.toByteArray();

				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, imageBlob.getContentType());

				// Resmi yanıt olarak döndürdük
				return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
			} catch (IOException e) {
				// Hata oluştuğunda dönecek response
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
		} else {
			// Kullanıcı için profil fotoğrafı bulunamadıysa dönecek response
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return null;
		}
	}

	@DeleteMapping("/{userId}/delete-profile-image")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('EMPLOYER')")
	public Result deleteById(@PathVariable("userId") Long userId) {
		return this.userService.deleteProfileImage(userId.toString());
	}
}
