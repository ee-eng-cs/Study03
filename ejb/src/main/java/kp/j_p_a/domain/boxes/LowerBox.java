package kp.j_p_a.domain.boxes;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * The <B>lower box</B> entity class.
 *
 */
@Entity
public class LowerBox extends Box {

	@ManyToOne
	private CentralBox centralBox;

	/**
	 * Gets the central box.
	 * 
	 * @return the central box
	 */
	public CentralBox getCentralBox() {
		return centralBox;
	}

	/**
	 * Sets the central box.
	 * 
	 * @param centralBox
	 *            the central box to set
	 */
	public void setCentralBox(CentralBox centralBox) {
		this.centralBox = centralBox;
	}
}