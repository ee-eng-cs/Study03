package kp.trans_c_m_t.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import kp.trans_c_m_t.domain.Parcel;

/**
 * The stateless session bean for parcel administrator.
 *
 */
@Stateless
public class ParcelAdministratorBean {
	/*-
	 Using Container-Managed Transactions.
	 
	 Transaction Attributes: the 'Required' attribute is the implicit transaction attribute
	 for all enterprise bean methods running with container-managed transaction demarcation. 
	*/

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private AuditorBean auditorBean;

	@Inject
	private EntityManager entityManager;

	/**
	 * Creates the parcel.
	 * 
	 * @param text
	 *            the text
	 * @return the id
	 */
	@Transactional
	public int create(String text) {

		final Parcel parcel = new Parcel(text);
		entityManager.persist(parcel);
		report.add(String.format("Created parcel: id[%d], text[%s].", parcel.getId(), parcel.getText()));
		auditorBean.recordCreated();
		logger.info(String.format("create(): parcel: id[%d], text[%s].", parcel.getId(), parcel.getText()));
		return parcel.getId();
	}

	/**
	 * Reads the parcel list.
	 * 
	 */
	public void read() {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Parcel> criteriaQuery = criteriaBuilder.createQuery(Parcel.class);
		criteriaQuery.from(Parcel.class);
		final TypedQuery<Parcel> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Parcel> parcelList = typedQuery.getResultList();
		for (Parcel parcel : parcelList) {
			report.add(String.format("parcel: id[%d], text[%s].", parcel.getId(), parcel.getText()));
		}
		logger.info(String.format("read(): parcelList size[%d]", parcelList.size()));
	}
}