package kp.trans_b_m_t.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity class for <B>capsule</B>.
 *
 */
@Entity
public class Capsule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String text;

	/**
	 * Constructor.
	 * 
	 */
	public Capsule() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param text the text
	 */
	public Capsule(String text) {
		super();
		this.text = text;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
}
