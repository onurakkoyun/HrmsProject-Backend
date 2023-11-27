package kodlamaio.hrms.business.concretes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;

import kodlamaio.hrms.business.abstracts.IUserService;
import kodlamaio.hrms.core.utilities.entities.User;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;

@Service
public class UserManager implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Value("${firebase.storageBucket}")
	private String storageBucket;

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<List<User>>(this.userDao.findAll(), "İş arayanlar listelendi");
	}

	@Override
	public DataResult<User> getByEmail(String email) {
		return new SuccessDataResult<User>(this.userDao.getByEmail(email), "Data listelendi");
	}

	@Override
	public DataResult<User> findUserById(Long id) {
		return new SuccessDataResult<User>(this.userDao.findById(id).orElseThrow(), "Data listed");
	}

	@Override
	public Result uploadProfileImage(Long userId, InputStream imageStream, long fileSize, String contentType) {
		User user = userDao.findById(userId).orElseThrow();

		try (InputStream stream = imageStream) {
		    BlobId blobId = BlobId.of(storageBucket, "user-profile-images/" + userId);
		    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType)
		            .setContentDisposition("inline; filename=" + userId + ".jpg").build();
		    Storage storage = StorageClient.getInstance().bucket().getStorage();
		    storage.create(blobInfo, stream.readAllBytes());
		} catch (IOException e) {
		    e.printStackTrace();
		}

		// Kullanıcının profil fotoğrafı URL'sini güncelle
		user.setProfilePhotoUrl("https://storage.googleapis.com/" + storageBucket + "/user-profile-images/" + userId);
		userDao.save(user);
		return new SuccessResult("Profil fotoğrafı yüklendi.");
	}

	@Override
	public Blob getImageBlob(String userId) {
		Storage storage = StorageClient.getInstance().bucket().getStorage();
		return storage.get(BlobId.of(storageBucket, "user-profile-images/" + userId));
	}

	@Override
	public Result deleteProfileImage(String userId) {
		try {
			// Kullanıcının profil fotoğrafını veritabanından ve Google Cloud Storage'dan
			// kaldırma işlemleri
			Blob imageBlob = getImageBlob(userId);

			if (imageBlob != null) {
				// Profil fotoğrafını veritabanından silme işlemi (Örnek: JPA veya Hibernate
				// kullanarak)
				userDao.deleteById(Long.parseLong(userId));

				// Profil fotoğrafını Google Cloud Storage'dan silme işlemi
				BlobId blobId = BlobId.of(storageBucket, "users/" + userId + "/profile-image.jpg"); // Örnek dosya yolu
				Storage storage = StorageClient.getInstance().bucket().getStorage();
				boolean deleted = storage.delete(blobId);

				if (deleted) {
					return new SuccessResult("Profil fotoğrafı başarıyla silindi");
				} else {
					return new ErrorResult("Profil fotoğrafı silme işlemi başarısız");
				}
			} else {
				return new ErrorResult("Kullanıcının profil fotoğrafı bulunamadı");
			}
		} catch (Exception e) {
			// Hata oluştuğunda uygun bir şekilde işleyin ve hatayı loglayın veya gerekli
			// işlemleri gerçekleştirin.
			return new ErrorResult("Profil fotoğrafı silme hatası: " + e.getMessage());
		}
	}

}
