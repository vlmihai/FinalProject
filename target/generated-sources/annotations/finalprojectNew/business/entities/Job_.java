package finalprojectNew.business.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Job.class)
public abstract class Job_ {

	public static volatile SingularAttribute<Job, Long> applicationCount;
	public static volatile SingularAttribute<Job, Boolean> isDeleted;
	public static volatile SingularAttribute<Job, String> name;
	public static volatile SingularAttribute<Job, Date> postedOn;
	public static volatile SingularAttribute<Job, String> description;
	public static volatile SingularAttribute<Job, Employer> employer;
	public static volatile SingularAttribute<Job, String> location;
	public static volatile SingularAttribute<Job, Long> id;
	public static volatile SingularAttribute<Job, Date> updatedOn;
	public static volatile SingularAttribute<Job, String> experience;
	public static volatile SingularAttribute<Job, String> type;
	public static volatile SingularAttribute<Job, String> status;

}

