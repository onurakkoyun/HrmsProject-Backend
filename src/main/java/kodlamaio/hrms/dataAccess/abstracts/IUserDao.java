package kodlamaio.hrms.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.core.utilities.entities.User;

public interface IUserDao extends JpaRepository<User, Long>{
	User getByEmail(String email);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

}
