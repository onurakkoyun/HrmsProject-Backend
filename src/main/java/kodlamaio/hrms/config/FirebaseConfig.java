package kodlamaio.hrms.config;

import com.google.cloud.storage.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

	@Value("${firebase.storageBucket}")
	private String storageBucket;

	@Value("${firebase.projectId}")
	private String projectId;

	private Storage storage;

	@PostConstruct
	public void init() throws IOException {
		// Firebase yapılandırma dosyasını yükle

		if (FirebaseApp.getApps().isEmpty()) {
			try {

				Resource resource = new ClassPathResource(
						"kodlamaio/hrms/config/hrms-1f737-firebase-adminsdk-13ho1-2e6409e27a.json");

				GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

				FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials)
						.setStorageBucket(storageBucket).setProjectId(projectId).build();

				FirebaseApp.initializeApp(options);
				storage = StorageOptions.newBuilder()
						.setCredentials(GoogleCredentials.fromStream(resource.getInputStream())).build().getService();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
