package kp.j_p_a.domain.levels;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Second Level entity class Metamodel.
 *
 */
@StaticMetamodel(SecondLevel.class)
public class SecondLevel_ extends Level_ {
	public static volatile SetAttribute<SecondLevel, ThirdLevel> thirdLevels;
}