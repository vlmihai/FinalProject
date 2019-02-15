package finalprojectNew.business.repository;


import finalprojectNew.business.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findByEmployerId(@Param("employerId") Long employerId);

	@Query(nativeQuery = true, value = "select * from job where employer_id = :employerId and is_deleted = :deleted")
	List<Job> findActiveJobsByEmployerId(@Param("employerId") Long employerId, @Param("deleted") Boolean deleted);

	List<Job> findByisDeleted(@Param("isDeleted") boolean isDeleted);

}