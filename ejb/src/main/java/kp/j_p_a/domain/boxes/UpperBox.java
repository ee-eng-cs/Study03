package kp.j_p_a.domain.boxes;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * The <B>upper box</B> entity class.
 *
 */
@Entity
public class UpperBox extends Box {

	// inverse side of bidirectional relationship
	@OneToMany(mappedBy = "upperBox")
	@OrderBy
	private Set<CentralBox> centralBoxes = new TreeSet<>();

	/**
	 * Adds central box.
	 * 
	 * @param centralBox the central box to add
	 */
	public void addCentralBox(CentralBox centralBox) {

		centralBoxes.add(centralBox);
		centralBox.setUpperBox(this);
	}

	/**
	 * Removes central box.
	 * 
	 * @param centralBox the central box to remove
	 */
	public void removeCentralBox(CentralBox centralBox) {

		centralBoxes.remove(centralBox);
		centralBox.setUpperBox(null);
	}

	/**
	 * Gets central boxes.
	 * 
	 * @return the central boxes set
	 */
	public Set<CentralBox> getCentralBoxes() {
		return centralBoxes;
	}
}