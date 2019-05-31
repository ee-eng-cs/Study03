package kp.trans_c_m_t.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity class for <B>audit</B>.
 *
 */
@Entity
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int created;

	private int approved;

	/**
	 * Gets the created.
	 * 
	 * @return the created
	 */
	public int getCreated() {
		return created;
	}

	/**
	 * Sets the created.
	 * 
	 * @param created the created to set
	 */
	public void setCreated(int created) {
		this.created = created;
	}

	/**
	 * Gets the approved.
	 * 
	 * @return the approved
	 */
	public int getApproved() {
		return approved;
	}

	/**
	 * Sets the approved.
	 * 
	 * @param approved the approved to set
	 */
	public void setApproved(int approved) {
		this.approved = approved;
	}
}