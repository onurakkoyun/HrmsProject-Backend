package kodlamaio.hrms.dataAccess.abstracts;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import kodlamaio.hrms.core.utilities.entities.RefreshToken;
import kodlamaio.hrms.core.utilities.entities.User;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	@Modifying
	Long deleteByUser(User user);
	
	List<RefreshToken> findAllByExpiryDateBefore(Instant expiryDate);
}
