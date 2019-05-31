package kp.trans_b_m_t.service;

import static javax.ejb.TransactionManagementType.BEAN;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import kp.Constants;
import kp.trans_b_m_t.domain.Capsule;

/**
 * The capsule administrator stateful session bean.<BR>
 * The bean-managed transaction management is used.
 * 
 */
@Stateful
@TransactionManagement(BEAN)
public class CapsuleAdministratorBean implements CapsuleAdministrator, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	private static final List<String> TEXT_LIST = IntStream.concat(/*-*/
			IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)), /*-*/
			IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))/*-*/
			.mapToObj(num -> Character.toString((char) num)).collect(Collectors.toList());

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void create() {
		try {
			if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
				userTransaction.begin();
				logger.info("create(): user transaction begin");
			}
			final String text = TEXT_LIST.get(0);
			Collections.rotate(TEXT_LIST, -1);
			final Capsule capsule = new Capsule(text);
			entityManager.persist(capsule);
			report.add(String.format("Created capsule: id[%d], text[%s].", capsule.getId(), capsule.getText()));
			logger.info(String.format("create(): capsule: id[%d], text[%s].", capsule.getId(), capsule.getText()));
		} catch (Exception e) {
			logger.severe(String.format("create(): exception[%s]", e.getMessage()));
			rollback();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void read() {

		try {
			if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
				userTransaction.begin();
				logger.info("read(): user transaction begin");
			}
			final List<Capsule> capsuleList = loadCapsuleList();
			if (capsuleList.isEmpty()) {
				report.add("empty");
			} else {
				for (Capsule capsule : capsuleList) {
					report.add(String.format("capsule: id[%d], text[%s].", capsule.getId(), capsule.getText()));
				}
			}
			logger.info(String.format("read(): capsuleList size[%d]", capsuleList.size()));
		} catch (Exception e) {
			logger.severe(String.format("read(): exception[%s]", e.getMessage()));
			rollback();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void delete() {

		try {
			if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
				userTransaction.begin();
				logger.info("delete(): user transaction begin");
			}
			final List<Capsule> capsuleList = loadCapsuleList();
			if (capsuleList.isEmpty()) {
				report.add("empty");
				logger.info("delete(): empty capsuleList");
				return;
			}
			// delete last in list
			final Capsule capsule = capsuleList.get(capsuleList.size() - 1);
			final int id = capsule.getId();
			final String text = capsule.getText();

			entityManager.remove(capsule);
			entityManager.flush();
			report.add(String.format("Deleted capsule: id[%d], text[%s].", id, text));
			logger.info(String.format("delete(): deleted capsule: id[%d], text[%s].", id, text));
		} catch (Exception e) {
			logger.severe(String.format("delete(): exception[%s]", e.getMessage()));
			rollback();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void commit() {

		try {
			if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
				userTransaction.commit();
				report.add(String.format("%s COMMIT", Constants.LINE));
			}
		} catch (Exception e) {
			logger.severe(String.format("commit(): exception[%s]", e.getMessage()));
			return;
		}
		logger.info("commit():");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void rollback() {

		try {
			if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
				userTransaction.rollback();
				report.add(String.format("%s ROLLBACK", Constants.LINE));
			}
		} catch (SystemException e) {
			logger.severe(String.format("rollback(): exception[%s]", e.getMessage()));
			return;
		}
		logger.info("rollback():");
	}

	/**
	 * Loads capsule list.
	 * 
	 * @return the capsule list
	 */
	private List<Capsule> loadCapsuleList() {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Capsule> criteriaQuery = criteriaBuilder.createQuery(Capsule.class);
		final Root<Capsule> capsuleRoot = criteriaQuery.from(Capsule.class);
		final Order order = criteriaBuilder.asc(capsuleRoot.get("id"));
		criteriaQuery.select(capsuleRoot).orderBy(order);
		final TypedQuery<Capsule> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}