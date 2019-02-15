package finalprojectNew.business.repository;


import finalprojectNew.business.entities.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
	Employer findByUsername(@Param("username") String username);
}
