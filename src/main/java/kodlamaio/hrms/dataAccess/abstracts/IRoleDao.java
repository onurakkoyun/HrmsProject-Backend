package kodlamaio.hrms.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.core.utilities.entities.ERole;
import kodlamaio.hrms.core.utilities.entities.Role;


public interface IRoleDao extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);

}
