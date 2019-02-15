package finalprojectNew.business.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AppUser.class)
public abstract class AppUser_ {

	public static volatile SingularAttribute<AppUser, String> password;
	public static volatile SingularAttribute<AppUser, String> role;
	public static volatile SingularAttribute<AppUser, Long> id;
	public static volatile SingularAttribute<AppUser, Boolean> enabled;
	public static volatile SingularAttribute<AppUser, String> username;

}

