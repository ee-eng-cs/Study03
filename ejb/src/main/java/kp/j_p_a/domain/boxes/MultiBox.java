package kp.j_p_a.domain.boxes;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

/**
 * The <B>multi box</B> entity class.
 *
 */
@Entity
public class MultiBox extends Box {

	@ManyToMany
	@OrderBy
	private Set<CentralBox> centralBoxes = new TreeSet<>();

	/**
	 * Gets central boxes.
	 * 
	 * @return the central boxes set
	 */
	public Set<CentralBox> getCentralBoxes() {
		return centralBoxes;
	}
}