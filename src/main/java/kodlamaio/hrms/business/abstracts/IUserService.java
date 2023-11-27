package kodlamaio.hrms.business.abstracts;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.google.cloud.storage.Blob;

import kodlamaio.hrms.core.utilities.entities.User;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;

public interface IUserService {
	
	DataResult<List<User>> getAll();
	
	DataResult<User> getByEmail(String email);
	
	DataResult<User> findUserById(Long id);
	
	Result uploadProfileImage(Long userId, InputStream imageStream, long fileSize, String contentType) throws IOException;

	Blob getImageBlob(String userId);
	
	Result deleteProfileImage(String userId);
}
