package kp.j_p_a.domain.levels;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * The entity class for <B>second level</B>.
 *
 */
@Entity
public class SecondLevel extends Level {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy
	private Set<ThirdLevel> thirdLevels = new TreeSet<>();

	/**
	 * Gets the third levels set.
	 * 
	 * @return the third levels set.
	 */
	public Set<ThirdLevel> getThirdLevels() {
		return thirdLevels;
	}
}