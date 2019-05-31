package kp.j_p_a.domain.boxes;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The <B>box</B> entity class.
 *
 */
@MappedSuperclass
public abstract class Box implements Comparable<Box> {
	/*- Mapped superclass inheritance:
	 * 
	 * Mapped superclasses do not have any corresponding tables in the underlying datastore.
	 * Entities that inherit from the mapped superclass define the table mappings.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String text;

	/**
	 * Gets id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets text.
	 * 
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Box other = (Box) obj;
		if (id != other.id) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compareTo(Box box) {

		if (Objects.isNull(box)) {
			return 0;
		}
		final int cmp = (int) (this.id - box.getId());
		return cmp == 0 ? this.text.compareTo(box.text) : cmp;
	}
}