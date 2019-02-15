package finalprojectNew.business.repository;


import finalprojectNew.business.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Candidate findByUsername(@Param("username") String username);

}