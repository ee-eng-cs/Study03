package kp.j_p_a.domain.levels;

import javax.persistence.MappedSuperclass;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Level mapped superclass Metamodel.
 *
 */
@MappedSuperclass
@StaticMetamodel(Level.class)
public abstract class Level_ {
	public static volatile SingularAttribute<Level, Integer> id;
	public static volatile SingularAttribute<Level, String> text;
}