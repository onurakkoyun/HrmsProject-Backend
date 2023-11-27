package kodlamaio.hrms.business.abstracts;

import java.util.Optional;

import kodlamaio.hrms.core.utilities.entities.RefreshToken;

public interface RefreshTokenService {
	
	Optional<RefreshToken> findByToken(String token);
	
	RefreshToken createRefreshToken(Long userId);

	RefreshToken verifyExpiration(RefreshToken token);
}
