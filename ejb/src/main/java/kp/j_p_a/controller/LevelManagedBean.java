package kp.j_p_a.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import kp.j_p_a.domain.levels.FirstLevel;
import kp.j_p_a.domain.levels.FirstLevel_;
import kp.j_p_a.domain.levels.FourthLevel;
import kp.j_p_a.domain.levels.FourthLevel_;
import kp.j_p_a.domain.levels.SecondLevel;
import kp.j_p_a.domain.levels.SecondLevel_;
import kp.j_p_a.domain.levels.ThirdLevel;
import kp.j_p_a.domain.levels.ThirdLevel_;

/**
 * The CDI managed bean for <B>level</B>.<BR>
 * Uses metamodel queries.
 *
 */
@Named
@RequestScoped
public class LevelManagedBean {
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
	 * Creates, reads, and deletes levels.
	 * 
	 * @return the result
	 */
	@Transactional
	public String createReadDeleteLevels() {

		report.clear();
		long count = reportCounts();
		if (count == 0) {
			createLevels();
		} else {
			readWithQueryUsingJoins();
			readWithCriteriaUsingJoins();
			readWithCriteriaUsingAggregateFunctions();
			deleteLevels();
		}
		reportCounts();
		logger.info("createReadDeleteLevels():");
		return "";
	}

	/**
	 * Reports counts.
	 * 
	 * @return count
	 */
	private long reportCounts() {

		final CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
		countCriteria.select(criteriaBuilder.countDistinct(countCriteria.from(FirstLevel.class)));
		final TypedQuery<Long> countQuery = entityManager.createQuery(countCriteria);
		return countQuery.getSingleResult();
	}

	/**
	 * Creates levels.
	 * 
	 */
	private void createLevels() {

		report.add("Before Persist");
		for (int i = 1; i <= 4; i++) {
			final FirstLevel firstLevel = new FirstLevel();
			entityManager.persist(firstLevel);
			firstLevel.setText(String.format("item %d", i));
			for (int j = 1; j <= 4; j++) {
				final SecondLevel secondLevel = new SecondLevel();
				entityManager.persist(secondLevel);
				secondLevel.setText(String.format("item %d.%d", i, i * j));
				firstLevel.getSecondLevels().add(secondLevel);
				for (int k = 1; k <= 4; k++) {
					final ThirdLevel thirdLevel = new ThirdLevel();
					entityManager.persist(thirdLevel);
					thirdLevel.setText(String.format("item %d.%d.%d", i, i * j, i * j * k));
					secondLevel.getThirdLevels().add(thirdLevel);
					for (int m = 1; m <= 4; m++) {
						final FourthLevel fourthLevel = new FourthLevel();
						entityManager.persist(fourthLevel);
						fourthLevel.setText(String.format("item %d.%d.%d.%d", i, i * j, i * j * k, i * j * k * m));
						thirdLevel.getFourthLevels().add(fourthLevel);
					}
				}
			}
		}
		report.add("After Persist");
	}

	/**
	 * Reads levels with query using joins.
	 * 
	 */
	private void readWithQueryUsingJoins() {

		final String[] queryArr = { /*-*/
				"SELECT DISTINCT fst FROM FirstLevel fst "/*-*/
						+ "JOIN fst.secondLevels snd JOIN snd.thirdLevels trd JOIN trd.fourthLevels fth "/*-*/
						+ "WHERE fth.text IN('item 1.1.1.1', 'item 4.16.64.256') "/*-*/
						+ "ORDER BY fst.id",
				"SELECT DISTINCT fst FROM FirstLevel fst, "/*-*/
						+ "IN(fst.secondLevels) snd, IN(snd.thirdLevels) trd, IN(trd.fourthLevels) fth "/*-*/
						+ "WHERE fth.text IN('item 1.1.1.1', 'item 4.16.64.256') "/*-*/
						+ "ORDER BY fst.id" };
		final String[] labelArr = { "*** String Query with 'JOIN' ***", "*** String Query with 'IN' ***" };
		for (int i = 0; i < queryArr.length; i++) {
			final TypedQuery<FirstLevel> query = entityManager.createQuery(queryArr[i], FirstLevel.class);
			final List<FirstLevel> firstLevelList = query.getResultList();
			readList(firstLevelList, labelArr[i]);
		}
	}

	/**
	 * Reads levels with criteria using joins.
	 * 
	 */
	private void readWithCriteriaUsingJoins() {

		final CriteriaQuery<FirstLevel> criteria = criteriaBuilder.createQuery(FirstLevel.class);
		final Root<FirstLevel> fstRoot = criteria.from(FirstLevel.class);
		final Join<FirstLevel, SecondLevel> sndJoin = fstRoot.join(FirstLevel_.secondLevels);
		final Join<SecondLevel, ThirdLevel> trdJoin = sndJoin.join(SecondLevel_.thirdLevels);
		final Join<ThirdLevel, FourthLevel> fthJoin = trdJoin.join(ThirdLevel_.fourthLevels);
		criteria.select(fstRoot).distinct(true);
		criteria.where(fthJoin.get(FourthLevel_.text).in("item 1.1.1.1", "item 4.16.64.256"));
		criteria.orderBy(criteriaBuilder.asc(fstRoot.get(FirstLevel_.id)));
		final TypedQuery<FirstLevel> query = entityManager.createQuery(criteria);
		final List<FirstLevel> firstLevelList = query.getResultList();
		readList(firstLevelList, "*** Criteria Query with 'join' ***");
	}

	/**
	 * Reads level list.
	 * 
	 * @param firstLevelList the first level list
	 * @param label          the label
	 */
	private void readList(List<FirstLevel> firstLevelList, String label) {

		report.add(label);
		/*
		 * First Element
		 */
		final FirstLevel fstLevelF = firstLevelList.get(0);
		if (Objects.isNull(fstLevelF)) {
			return;
		}
		final Iterator<SecondLevel> sndIterF = fstLevelF.getSecondLevels().iterator();
		if (!sndIterF.hasNext()) {
			return;
		}
		final SecondLevel sndLevelF = sndIterF.next();
		final Iterator<ThirdLevel> trdIterF = sndLevelF.getThirdLevels().iterator();
		if (!trdIterF.hasNext()) {
			return;
		}
		final ThirdLevel trdLevelF = trdIterF.next();
		final Iterator<FourthLevel> fthIterF = trdLevelF.getFourthLevels().iterator();
		if (!fthIterF.hasNext()) {
			return;
		}
		final FourthLevel fthLevelF = fthIterF.next();
		/*
		 * Last Element
		 */
		final FirstLevel fstLevelL = firstLevelList.get(firstLevelList.size() - 1);
		if (Objects.isNull(fstLevelF)) {
			return;
		}
		final Iterator<SecondLevel> sndIterL = fstLevelL.getSecondLevels().iterator();
		SecondLevel sndLevelL = null;
		while (sndIterL.hasNext()) {
			sndLevelL = sndIterL.next();
		}
		if (Objects.isNull(sndLevelL)) {
			return;
		}
		final Iterator<ThirdLevel> trdIterL = sndLevelL.getThirdLevels().iterator();
		ThirdLevel trdLevelL = null;
		while (trdIterL.hasNext()) {
			trdLevelL = trdIterL.next();
		}
		if (Objects.isNull(trdLevelL)) {
			return;
		}
		final Iterator<FourthLevel> fthIterL = trdLevelL.getFourthLevels().iterator();
		FourthLevel fthLevelL = null;
		while (fthIterL.hasNext()) {
			fthLevelL = fthIterL.next();
		}
		if (Objects.isNull(fthLevelL)) {
			return;
		}
		report.add(String.format(
				"1st Level: id[%d], text[%s], 2nd Level: id[%d], text[%s], "
						+ "3rd Level: id[%d], text[%s], 4th Level: id[%d], text[%s]",
				fstLevelF.getId(), fstLevelF.getText(), sndLevelF.getId(), sndLevelF.getText(), trdLevelF.getId(),
				trdLevelF.getText(), fthLevelF.getId(), fthLevelF.getText()));
		report.add(String.format(
				"1st Level: id[%d], text[%s], 2nd Level: id[%d], text[%s], "
						+ "3rd Level: id[%d], text[%s], 4th Level: id[%d], text[%s]",
				fstLevelL.getId(), fstLevelL.getText(), sndLevelL.getId(), sndLevelL.getText(), trdLevelL.getId(),
				trdLevelL.getText(), fthLevelL.getId(), fthLevelL.getText()));
	}

	/**
	 * Reads levels with criteria using aggregate functions.
	 * 
	 */
	private void readWithCriteriaUsingAggregateFunctions() {

		final CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
		final Root<FirstLevel> fstRoot = criteria.from(FirstLevel.class);
		final Join<FirstLevel, SecondLevel> sndJoin = fstRoot.join(FirstLevel_.secondLevels);
		final Join<SecondLevel, ThirdLevel> trdJoin = sndJoin.join(SecondLevel_.thirdLevels);
		final Join<ThirdLevel, FourthLevel> fthJoin = trdJoin.join(ThirdLevel_.fourthLevels);

		final Path<Integer> idFst = fstRoot.get(FirstLevel_.id);
		final Expression<Integer> modSnd = criteriaBuilder.mod(sndJoin.get(SecondLevel_.id), 3);
		final Expression<Integer> modTrd = criteriaBuilder.mod(trdJoin.get(ThirdLevel_.id), 3);
		final Expression<Long> cntFth = criteriaBuilder.countDistinct(fthJoin.get(FourthLevel_.id));

		criteria.multiselect(idFst, modSnd, modTrd, cntFth);
		criteria.groupBy(idFst, modSnd, modTrd);
		criteria.having(fstRoot.get(FirstLevel_.text).in("item 1", "item 2", "item 3"));
		criteria.where(fstRoot.get(FirstLevel_.text).in("item 2", "item 3", "item 4"));
		criteria.orderBy(criteriaBuilder.asc(idFst), criteriaBuilder.asc(modSnd), criteriaBuilder.asc(modTrd));
		final TypedQuery<Object[]> query = entityManager.createQuery(criteria);
		final List<Object[]> resultList = query.getResultList();

		report.add("*** Criteria Query with 'groupBy' ***");
		for (Object[] result : resultList) {
			report.add(String.format(
					"1st Level id[%d], 2nd Level modulo 3[%d], 3rd Level modulo 3[%d], 4th Level count[%d]", /*-*/
					result[0], result[1], result[2], result[3]));
		}
	}

	/**
	 * Deletes level.
	 * 
	 */
	private void deleteLevels() {

		report.add("Before Remove");
		final CriteriaDelete<FirstLevel> deleteCriteria = criteriaBuilder.createCriteriaDelete(FirstLevel.class);
		deleteCriteria.from(FirstLevel.class);
		entityManager.createQuery(deleteCriteria).executeUpdate();
		report.add("After Remove");

	}
}