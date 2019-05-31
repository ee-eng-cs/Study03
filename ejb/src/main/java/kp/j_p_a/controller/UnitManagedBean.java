package kp.j_p_a.controller;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import kp.j_p_a.domain.units.Side;
import kp.j_p_a.domain.units.Unit;

/**
 * The CDI managed bean for <B>unit</B>.
 *
 */
@Named
@RequestScoped
public class UnitManagedBean {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private EntityManager entityManager;

	private CriteriaBuilder criteriaBuilder;

	/**
	 * Initializes bean.
	 * 
	 */
	@PostConstruct
	private void init() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/**
	 * Creates, reads, and deletes units.
	 * 
	 * @return the result
	 */
	@Transactional
	public String createReadDeleteUnits() {

		report.clear();
		long count = reportCounts();
		if (count == 0) {
			createUnits();
		} else {
			readAndDeleteUnits();
		}
		reportCounts();
		return "";
	}

	/**
	 * Reports counts.
	 * 
	 * @return the count
	 */
	private long reportCounts() {

		final CriteriaQuery<Tuple> tupleCriteria = criteriaBuilder.createTupleQuery();
		tupleCriteria.select(criteriaBuilder.tuple(criteriaBuilder.countDistinct(tupleCriteria.from(Unit.class)),
				criteriaBuilder.countDistinct(tupleCriteria.from(Side.class))));
		final TypedQuery<Tuple> tupleQuery = entityManager.createQuery(tupleCriteria);
		final Tuple tuple = tupleQuery.getSingleResult();

		report.add(String.format("Total units count[%d], total sides count[%d]", (Long) tuple.get(0),
				(Long) tuple.get(1)));
		return (Long) tuple.get(0);
	}

	/**
	 * Creates units.
	 * 
	 */
	private void createUnits() {

		final Unit unitA = new Unit("A");
		final Unit unitB = new Unit("B", unitA);
		final Unit unitC = new Unit("C", unitB);

		final Unit unitX = new Unit("X");
		unitC.addChild(unitX);
		final Unit unitY = new Unit("Y");
		unitC.addChild(unitY);
		final Unit unitZ = new Unit("Z");
		unitC.addChild(unitZ);

		report.add("Before Persist");
		entityManager.persist(unitA);
		entityManager.persist(unitB);
		entityManager.persist(unitC);
		report.add("After Persist");
		logger.info("createUnits():");
	}

	/**
	 * Reads and deletes units.
	 * 
	 */
	private void readAndDeleteUnits() {

		final Unit unitA = entityManager.find(Unit.class, "A");
		final Unit unitB = unitA.getNext();
		final Unit unitC = unitB.getNext();
		final Iterator<Unit> iterator = unitC.getChildren().iterator();
		final Unit unitX = iterator.next();
		final Unit unitY = iterator.next();
		final Unit unitZ = iterator.next();

		report.add(unitA.toString());
		report.add(unitB.toString());
		report.add(unitC.toString());
		report.add(unitX.toString());
		report.add(unitY.toString());
		report.add(unitZ.toString());

		report.add("Before Remove");
		entityManager.remove(unitA);
		report.add("After Remove");
		logger.info("readAndDeleteUnits():");
	}
}