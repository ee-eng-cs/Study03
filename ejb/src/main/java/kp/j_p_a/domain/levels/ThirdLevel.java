package kp.j_p_a.domain.levels;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * The entity class for <B>third level</B>.
 *
 */
@Entity
public class ThirdLevel extends Level {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy
	private Set<FourthLevel> fourthLevels = new TreeSet<>();

	/**
	 * Gets the fourth levels set.
	 * 
	 * @return the fourth levels set.
	 */
	public Set<FourthLevel> getFourthLevels() {
		return fourthLevels;
	}
}