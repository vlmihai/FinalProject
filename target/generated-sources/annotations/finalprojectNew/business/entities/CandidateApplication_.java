package finalprojectNew.business.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CandidateApplication.class)
public abstract class CandidateApplication_ {

	public static volatile SingularAttribute<CandidateApplication, Candidate> candidate;
	public static volatile SingularAttribute<CandidateApplication, Date> appliedOn;
	public static volatile SingularAttribute<CandidateApplication, String> applicationStatus;
	public static volatile SingularAttribute<CandidateApplication, Long> id;
	public static volatile SingularAttribute<CandidateApplication, Job> job;
	public static volatile SingularAttribute<CandidateApplication, Date> employerActionOn;

}

