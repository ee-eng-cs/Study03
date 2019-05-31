package kp.j_p_a.domain.levels;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * First Level entity class Metamodel.
 *
 */
@StaticMetamodel(FirstLevel.class)
public class FirstLevel_ extends Level_ {
	public static volatile SetAttribute<FirstLevel, SecondLevel> secondLevels;
}
