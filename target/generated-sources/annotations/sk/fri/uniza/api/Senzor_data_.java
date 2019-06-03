package sk.fri.uniza.api;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Senzor_data.class)
public abstract class Senzor_data_ {

	public static volatile SingularAttribute<Senzor_data, String> kto;
	public static volatile SingularAttribute<Senzor_data, Long> id;
	public static volatile SingularAttribute<Senzor_data, Integer> co;
	public static volatile SingularAttribute<Senzor_data, DateTime> kedy;

	public static final String KTO = "kto";
	public static final String ID = "id";
	public static final String CO = "co";
	public static final String KEDY = "kedy";

}

