package kp.trans_b_m_t.service;

import javax.ejb.Local;

/**
 * The capsule administrator.<BR>
 * This is the local business interface.
 *
 */
@Local
public interface CapsuleAdministrator {
	/**
	 * Creates capsule.
	 */
	void create();

	/**
	 * Reads capsule.
	 */
	void read();

	/**
	 * Deletes capsule.
	 */
	void delete();

	/**
	 * Commits transaction.
	 */
	void commit();

	/**
	 * Rollbacks transaction.
	 */
	void rollback();
}