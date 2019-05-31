package kp.j_p_a.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import kp.j_p_a.domain.boxes.CentralBox;
import kp.j_p_a.domain.boxes.LowerBox;
import kp.j_p_a.domain.boxes.MultiBox;
import kp.j_p_a.domain.boxes.SingleBox;
import kp.j_p_a.domain.boxes.UpperBox;
import kp.j_p_a.domain.components.TermDates;

/**
 * The stateless session bean for <B>box</B>.<BR>
 * Uses container-managed transactions.
 *
 */
@Named
@Stateless
public class BoxBean {

	private static final boolean USE_CRITERIA = true;

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Resource
	private EJBContext ejbContext;

	@Inject
	private EntityManager entityManager;

	private CriteriaBuilder criteriaBuilder;

	private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final Class<?>[] BOX_CLASSES_ARR = { CentralBox.class, UpperBox.class, SingleBox.class,
			MultiBox.class, LowerBox.class };
	private static final List<String> TEXT_LIST = IntStream.concat(/*-*/
			IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)), /*-*/
			IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))/*-*/
			.mapToObj(num -> Character.toString((char) num)).collect(Collectors.toList());
	private static final String BREAK = IntStream.rangeClosed(1, 80).boxed().map(arg -> "- ")
			.collect(Collectors.joining());

	/**
	 * Initializes bean.
	 * 
	 */
	@PostConstruct
	private void init() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/**
	 * Creates the central box.
	 * 
	 * @return the result
	 */
	public String createCentralBox() {

		/*
		 * Method uses bean-managed transaction.
		 */
		report.clear();
		try {
			final CentralBox centralBox = createAndFillCentralBox();
			if (getNumberOfCentralBoxes() > 3) {
				// simulate validation error
				ejbContext.setRollbackOnly();
				final String msg = "The maximum number of boxes is 3. The creating was aborted and the transaction was marked for rollback.";
				report.add(msg);
				logger.warning(String.format("createCentralBox(): %s", msg));
				return "";
			}
			loadLinesFromBox(centralBox);
		} catch (Exception e) {
			logger.severe(String.format("createCentralBox(): exception[%s]", e.getMessage()));
			throw new RuntimeException(e);
		}
		logger.info("createCentralBox():");
		return "";
	}

	/**
	 * Reads central boxes.
	 * 
	 * @return the result
	 */
	public String readCentralBoxes() {

		report.clear();
		showBoxesCount();
		final List<CentralBox> centralBoxList = findCentralBoxes();
		if (centralBoxList.isEmpty()) {
			report.add("empty");
			logger.info("readCentralBoxes(): empty");
			return "";
		}
		for (CentralBox centralBox : centralBoxList) {
			report.add(BREAK);
			loadLinesFromBox(centralBox);
		}
		logger.info(String.format("readCentralBoxes(): centralBoxList size[%d]", centralBoxList.size()));
		return "";
	}

	/**
	 * Updates central boxes.
	 * 
	 * @return the result
	 */
	public String updateCentralBoxes() {

		/*
		 * Method uses bean-managed transaction.
		 */
		report.clear();
		showBoxesCount();
		if (getNumberOfCentralBoxes() < 1) {
			report.add("empty");
			logger.info("updateCentralBoxes(): nothing to update");
			return "";
		}
		List<CentralBox> centralBoxList = null;
		try {
			centralBoxList = findCentralBoxes();
			for (CentralBox centralBox : centralBoxList) {
				centralBox.setCardinalDirection(centralBox.getCardinalDirection().getNext());
				entityManager.merge(centralBox);
			}
		} catch (Exception e) {
			logger.severe(String.format("updateCentralBoxes(): exception[%s]", e.getMessage()));
			throw new RuntimeException(e);
		}
		for (CentralBox centralBox : centralBoxList) {
			report.add(BREAK);
			loadLinesFromBox(centralBox);
		}
		logger.info("updateCentralBoxes():");
		return "";
	}

	/**
	 * Deletes the central box.
	 * 
	 * @return the result
	 */
	public String deleteCentralBox() {

		report.clear();
		if (getNumberOfCentralBoxes() < 1) {
			showBoxesCount();
			report.add("empty");
			logger.info("deleteCentralBox(): nothing to delete");
			return "";
		}
		try {
			final CentralBox centralBox = findCentralBoxForRemove();
			entityManager.remove(centralBox);
		} catch (Exception e) {
			logger.severe(String.format("deleteCentralBox(): exception[%s]", e.getMessage()));
			throw new RuntimeException(e);
		}
		showBoxesCount();
		logger.info("deleteCentralBox():");
		return "";
	}

	/**
	 * Creates and fills the central box.
	 * 
	 * @return the centralBox
	 */
	private CentralBox createAndFillCentralBox() {

		final CentralBox centralBox = new CentralBox();
		entityManager.persist(centralBox);
		centralBox.setText(TEXT_LIST.get(0));
		centralBox.setTermDates(new TermDates());

		addUpperBox(centralBox);

		final String label = String.format("created by %s", TEXT_LIST.get(0));

		final SingleBox singleBox = new SingleBox();
		singleBox.setText(label);
		singleBox.setCentralBox(centralBox);
		centralBox.setSingleBox(singleBox);

		createMultiplicity(centralBox, label);

		for (int i = 1; i <= 2; i++) {
			final LowerBox lowerBox = new LowerBox();
			lowerBox.setText(String.format("%s - item %d", label, i));
			centralBox.addLowerBox(lowerBox);
		}
		entityManager.merge(centralBox);
		Collections.rotate(TEXT_LIST, -1);
		return centralBox;
	}

	/**
	 * Adds the upper box.
	 * 
	 * @param centralBox the centralBox
	 */
	private void addUpperBox(CentralBox centralBox) {

		final CriteriaQuery<UpperBox> criteria = criteriaBuilder.createQuery(UpperBox.class);
		criteria.select(criteria.from(UpperBox.class));
		final TypedQuery<UpperBox> upperBoxQuery = entityManager.createQuery(criteria);
		final List<UpperBox> upperBoxList = upperBoxQuery.getResultList();
		final UpperBox upperBox;
		if (upperBoxList.isEmpty()) {
			upperBox = new UpperBox();
			upperBox.setText("root");
		} else {
			upperBox = upperBoxList.get(0);
		}
		upperBox.addCentralBox(centralBox);
	}

	/**
	 * Creates the multiplicity.
	 * 
	 * @param centralBox the centralBox
	 * @param label      the label
	 */
	private void createMultiplicity(CentralBox centralBox, String label) {

		final MultiBox multiBox = new MultiBox();
		multiBox.setText(label);

		final CriteriaQuery<CentralBox> cbCriteria = criteriaBuilder.createQuery(CentralBox.class);
		cbCriteria.select(cbCriteria.from(CentralBox.class));
		final TypedQuery<CentralBox> cbQuery = entityManager.createQuery(cbCriteria);
		final List<CentralBox> centralBoxList = cbQuery.getResultList();
		centralBoxList.stream().forEach(cb -> cb.addMultiBox(multiBox));

		final CriteriaQuery<MultiBox> mbCriteria = criteriaBuilder.createQuery(MultiBox.class);
		mbCriteria.select(mbCriteria.from(MultiBox.class));
		final TypedQuery<MultiBox> mbQuery = entityManager.createQuery(mbCriteria);
		final List<MultiBox> multiBoxList = mbQuery.getResultList();
		multiBoxList.stream().forEach(mb -> centralBox.addMultiBox(mb));
	}

	/**
	 * Finds central boxes.
	 * 
	 * @return the centralBoxList
	 */
	private List<CentralBox> findCentralBoxes() {

		TypedQuery<CentralBox> query = null;
		if (USE_CRITERIA) {
			final CriteriaQuery<CentralBox> criteria = criteriaBuilder.createQuery(CentralBox.class);
			final Root<CentralBox> centralBoxRoot = criteria.from(CentralBox.class);
			centralBoxRoot.fetch("multiBoxes");
			centralBoxRoot.fetch("lowerBoxes");
			final Order order = criteriaBuilder.asc(centralBoxRoot.get("id"));
			criteria.select(centralBoxRoot).distinct(true).orderBy(order);
			query = entityManager.createQuery(criteria);
		} else {
			query = entityManager.createNamedQuery("CentralBox.findAll", CentralBox.class);
		}
		final List<CentralBox> centralBoxList = query.getResultList();
		return centralBoxList;
	}

	/**
	 * Finds the central box for remove.
	 * 
	 * @return the centralBox
	 */
	private CentralBox findCentralBoxForRemove() {

		CentralBox centralBox = null;
		if (USE_CRITERIA) {
			final CriteriaQuery<CentralBox> criteriaQuery = criteriaBuilder.createQuery(CentralBox.class);
			final Root<CentralBox> centralBoxRoot = criteriaQuery.from(CentralBox.class);
			final Subquery<CentralBox> subQuery = criteriaQuery.subquery(CentralBox.class);
			final Root<CentralBox> centralBoxSubRoot = subQuery.from(CentralBox.class);
			subQuery.select(centralBoxSubRoot.get("id")).distinct(true);
			final Predicate predicate = criteriaBuilder.lessThanOrEqualTo(centralBoxRoot.get("id"),
					criteriaBuilder.all(subQuery));
			criteriaQuery.where(predicate);
			final TypedQuery<CentralBox> cbQuery = entityManager.createQuery(criteriaQuery);
			centralBox = cbQuery.getSingleResult();
		} else {
			final TypedQuery<CentralBox> query = entityManager.createNamedQuery("CentralBox.findFirst",
					CentralBox.class);
			centralBox = query.getSingleResult();
		}
		clearMultiplicityBeforeRemove(centralBox);
		return centralBox;
	}

	/**
	 * Clears the multiplicity before remove.
	 * 
	 * @param centralBox the centralBox
	 */
	private void clearMultiplicityBeforeRemove(CentralBox centralBox) {

		centralBox.getMultiBoxes().stream().forEach(mb -> centralBox.removeMultiBox(mb));

		final CriteriaQuery<MultiBox> mbCriteria = criteriaBuilder.createQuery(MultiBox.class);
		mbCriteria.select(mbCriteria.from(MultiBox.class));
		final TypedQuery<MultiBox> mbQuery = entityManager.createQuery(mbCriteria);
		final List<MultiBox> multiBoxList = mbQuery.getResultList();

		final CriteriaQuery<CentralBox> cbCriteria = criteriaBuilder.createQuery(CentralBox.class);
		cbCriteria.select(cbCriteria.from(CentralBox.class));
		final TypedQuery<CentralBox> cbQuery = entityManager.createQuery(cbCriteria);
		final List<CentralBox> centralBoxList = cbQuery.getResultList();

		for (MultiBox multiBox : multiBoxList) {
			if (!multiBox.getCentralBoxes().isEmpty()) {
				continue;
			}
			// remove empty multi box
			centralBoxList.stream().forEach(cb -> cb.removeMultiBox(multiBox));
			entityManager.remove(multiBox);
		}
	}

	/**
	 * Gets the number of central boxes.
	 * 
	 * @return the number
	 */
	private long getNumberOfCentralBoxes() {

		final CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
		countCriteria.select(criteriaBuilder.countDistinct(countCriteria.from(CentralBox.class)));
		final TypedQuery<Long> countQuery = entityManager.createQuery(countCriteria);
		return countQuery.getSingleResult();
	}

	/**
	 * Loads lines from the box.
	 * 
	 * @param centralBox the centralBox
	 */
	private void loadLinesFromBox(CentralBox centralBox) {

		report.add(String.format("centralBox: id[%d], text[%s], cardinalDirection[%s]", centralBox.getId(),
				centralBox.getText(), centralBox.getCardinalDirection()));
		final TermDates termDates = centralBox.getTermDates();
		report.add(String.format("date[%s], calendar[%s], localDate[%s], localDateTime[%s]",
				DATE_TIME_FORMAT.format(termDates.getDate()),
				DATE_TIME_FORMAT.format(termDates.getCalendar().getTime()),
				termDates.getLocalDate().format(DATE_FORMATTER),
				termDates.getLocalDateTime().format(DATE_TIME_FORMATTER)));

		/*
		 * Many-To-One
		 */
		final UpperBox upperBox = centralBox.getUpperBox();
		if (Objects.nonNull(upperBox)) {
			report.add(String.format("'Many-To-One' upperBox: id[%d], text[%s]", upperBox.getId(), upperBox.getText()));
		} else {
			report.add("'Many-To-One' NULL upperBox");
		}
		/*
		 * One-To-One
		 */
		final SingleBox singleBox = centralBox.getSingleBox();
		if (Objects.nonNull(singleBox)) {
			report.add(
					String.format("'One-To-One' singleBox: id[%d], text[%s]", singleBox.getId(), singleBox.getText()));
		} else {
			report.add("'One-To-One' NULL singleBox");
		}
		/*
		 * Many-To-Many
		 */
		final Set<MultiBox> multiBoxes = centralBox.getMultiBoxes();
		if (!multiBoxes.isEmpty()) {
			for (MultiBox multiBox : multiBoxes) {
				report.add(String.format("'Many-To-Many' multiBox: id[%d], text[%s]", multiBox.getId(),
						multiBox.getText()));
			}
		} else {
			report.add("'Many-To-Many' EMPTY multiBoxes");
		}
		/*
		 * One-To-Many
		 */
		final Set<LowerBox> lowerBoxes = centralBox.getLowerBoxes();
		if (!lowerBoxes.isEmpty()) {
			for (LowerBox lowerBox : lowerBoxes) {
				report.add(String.format("'One-To-Many' lowerBox: id[%d], text[%s]", lowerBox.getId(),
						lowerBox.getText()));
			}
		} else {
			report.add("'One-To-Many' EMPTY lowerBoxes");
		}
	}

	/**
	 * Shows the boxes count.
	 * 
	 */
	private void showBoxesCount() {

		final Long[] countArr = new Long[BOX_CLASSES_ARR.length];
		for (int i = 0; i < BOX_CLASSES_ARR.length; i++) {
			final CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
			countCriteria.select(criteriaBuilder.countDistinct(countCriteria.from(BOX_CLASSES_ARR[i])));
			final TypedQuery<Long> countQuery = entityManager.createQuery(countCriteria);
			countArr[i] = countQuery.getSingleResult();
		}
		report.add(String.format(
				"Total count: CentralBoxes[%d], UpperBoxes[%d], SingleBoxes[%d], MultiBoxes[%d], LowerBoxes[%d]",
				countArr[0], countArr[1], countArr[2], countArr[3], countArr[4]));
	}
}