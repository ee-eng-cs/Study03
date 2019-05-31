package kp.trans_b_m_t.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kp.trans_b_m_t.service.CapsuleAdministrator;

/**
 * CDI managed bean for bean-managed transactions research.
 *
 */
@Named
@SessionScoped
public class TransBmtManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CapsuleAdministrator capsuleAdministrator;

	/**
	 * Creates capsule.
	 * 
	 * @return the result
	 */
	public String create() {
		capsuleAdministrator.create();
		return "";
	}

	/**
	 * Reads capsule.
	 * 
	 * @return the result
	 */
	public String read() {
		capsuleAdministrator.read();
		return "";
	}

	/**
	 * Deletes capsule.
	 * 
	 * @return the result
	 */
	public String delete() {
		capsuleAdministrator.delete();
		return "";
	}

	/**
	 * Commits transaction.
	 * 
	 * @return the result
	 */
	public String commit() {
		capsuleAdministrator.commit();
		return "";
	}

	/**
	 * Rollbacks transaction.
	 * 
	 * @return the result
	 */
	public String rollback() {
		capsuleAdministrator.rollback();
		return "";
	}
}