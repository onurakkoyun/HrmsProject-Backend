package kodlamaio.hrms.business.concretes;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import kodlamaio.hrms.business.abstracts.RefreshTokenService;
import kodlamaio.hrms.core.utilities.entities.RefreshToken;
import kodlamaio.hrms.dataAccess.abstracts.IUserDao;
import kodlamaio.hrms.dataAccess.abstracts.RefreshTokenDao;
import kodlamaio.hrms.exceptions.TokenRefreshException;

@Service
public class RefreshTokenManager implements RefreshTokenService {

	@Value("${bezkoder.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Autowired
	private RefreshTokenDao refreshTokenDao;

	@Autowired
	private IUserDao userDao;

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenDao.findByToken(token);
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {

		cleanExpiredRefreshTokens();

		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userDao.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenDao.save(refreshToken);
		return refreshToken;
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenDao.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	private void cleanExpiredRefreshTokens() {
		List<RefreshToken> expiredTokens = refreshTokenDao.findAllByExpiryDateBefore(Instant.now());
		refreshTokenDao.deleteAll(expiredTokens);
	}

	@Transactional
	public Long deleteByUserId(Long userId) {
		return refreshTokenDao.deleteByUser(userDao.findById(userId).get());
	}

}
