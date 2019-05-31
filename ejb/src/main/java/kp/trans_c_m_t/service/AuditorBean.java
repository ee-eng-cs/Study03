package kp.trans_c_m_t.service;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import kp.trans_c_m_t.domain.Audit;

/**
 * The stateless session bean for <B>auditor</B>.
 * 
 */
@Stateless
public class AuditorBean {
	/*-
	 With the 'RequiresNew' attribute the container
	 suspends the current transaction and starts a new transaction.
	 The Java EE transaction manager does not support nested transactions.
	 */
	@Inject
	private List<String> report;

	@Inject
	private EntityManager entityManager;

	/**
	 * Records 'created'.<BR>
	 * The parcel status: created but not approved yet.
	 * 
	 */
	@TransactionAttribute(REQUIRES_NEW)
	public void recordCreated() {

		final Audit audit = loadAudit();
		audit.setCreated(audit.getCreated() + 1);
	}

	/**
	 * Records 'approved'.<BR>
	 * The parcel status: created and approved.
	 * 
	 */
	@TransactionAttribute(REQUIRES_NEW)
	public void recordApproved() {

		final Audit audit = loadAudit();
		audit.setApproved(audit.getApproved() + 1);
	}

	/**
	 * Shows the audit.
	 * 
	 */
	public void showAudit() {

		final Audit audit = loadAudit();
		report.add(String.format("Audit: parcels created[%d], parcels approved[%d].", audit.getCreated(),
				audit.getApproved()));
	}

	/**
	 * Loads the audit.
	 * 
	 * @return the audit
	 */
	@Transactional
	private Audit loadAudit() {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Audit> criteriaQuery = criteriaBuilder.createQuery(Audit.class);
		criteriaQuery.from(Audit.class);
		final TypedQuery<Audit> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Audit> recordBookList = typedQuery.getResultList();
		final Audit audit;
		if (recordBookList.isEmpty()) {
			audit = new Audit();
			entityManager.persist(audit);
		} else {
			audit = recordBookList.get(0);
		}
		return audit;
	}
}