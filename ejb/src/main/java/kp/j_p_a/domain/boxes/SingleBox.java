package kp.j_p_a.domain.boxes;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * The <B>single box</B> entity class.
 *
 */
@Entity
public class SingleBox extends Box {

	// inverse side of bidirectional relationship
	@OneToOne(mappedBy = "singleBox")
	private CentralBox centralBox;

	/**
	 * Gets central box.
	 * 
	 * @return the central box
	 */
	public CentralBox getCentralBox() {
		return centralBox;
	}

	/**
	 * Sets central box.
	 * 
	 * @param centralBox the central box to set
	 */
	public void setCentralBox(CentralBox centralBox) {
		this.centralBox = centralBox;
	}
}