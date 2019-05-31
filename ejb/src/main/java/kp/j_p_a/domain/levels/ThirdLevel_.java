package kp.j_p_a.domain.levels;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Third Level entity class Metamodel.
 *
 */
@StaticMetamodel(ThirdLevel.class)
public class ThirdLevel_ extends Level_ {
	public static volatile SetAttribute<ThirdLevel, FourthLevel> fourthLevels;
}