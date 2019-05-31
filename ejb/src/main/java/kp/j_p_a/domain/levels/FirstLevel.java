package kp.j_p_a.domain.levels;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * The entity class for <B>first level</B>.
 *
 */
@Entity
public class FirstLevel extends Level {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy
	private Set<SecondLevel> secondLevels = new TreeSet<>();

	/**
	 * Gets the second levels set.
	 * 
	 * @return the second levels set.
	 */
	public Set<SecondLevel> getSecondLevels() {
		return secondLevels;
	}
}