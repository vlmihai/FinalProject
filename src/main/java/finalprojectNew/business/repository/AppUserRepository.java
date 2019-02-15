package finalprojectNew.business.repository;

import finalprojectNew.business.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsernameIgnoreCase(@Param("username") String username);
}