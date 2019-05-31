package kp.e_j_b.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kp.Constants;
import kp.e_j_b.Common;
import kp.e_j_b.remote.Sing;
import kp.e_j_b.remote.StaFu;
import kp.e_j_b.remote.StaLe;
import kp.util.Tools;

/**
 * CDI Managed Bean for Tasks.
 *
 */
@Named
@ApplicationScoped
@Singleton
public class TasksManagedBean {

	@Resource(name = "java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService executorService;

	// stateless
	@EJB
	private StaLe slb1, slb2, slb3, slb4, slb5;

	// singleton
	@EJB
	private Sing sgb1, sgb2, sgb3, sgb4, sgb5;

	// stateful
	@EJB
	private StaFu sfb1, sfb2, sfb3, sfb4, sfb5;

	@Inject
	private List<String> report;

	private static final List<String> TEXT_LIST = IntStream.concat(/*-*/
			IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)), /*-*/
			IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))/*-*/
			.mapToObj(num -> Character.toString((char) num)).collect(Collectors.toList());

	/**
	 * Researches stateless beans.
	 * 
	 * @return the result
	 */
	public String researchStateless() {

		submitTasks(new Common[] { slb1, slb2, slb3, slb4, slb5 }, "*** Stateless session beans: ");
		final String message = String.format(
				"*** Stateless session beans injected objects hash codes: slb1[%s], slb2[%s], slb3[%s], slb4[%s], slb5[%s]",
				Tools.hashCodeFormated(slb1), Tools.hashCodeFormated(slb2), Tools.hashCodeFormated(slb3),
				Tools.hashCodeFormated(slb4), Tools.hashCodeFormated(slb5));
		report.add(message);
		report.add(Constants.LINE);
		return "";
	}

	/**
	 * Researches singleton beans.
	 * 
	 * @return the result
	 */
	public String researchSingleton() {

		submitTasks(new Common[] { sgb1, sgb2, sgb3, sgb4, sgb5 }, "*** Singleton session beans: ");
		final String message = String.format(
				"*** Singleton session beans injected objects hash codes: sgb1[%s], sgb2[%s], sgb3[%s], sgb4[%s], sgb5[%s]",
				Tools.hashCodeFormated(sgb1), Tools.hashCodeFormated(sgb2), Tools.hashCodeFormated(sgb3),
				Tools.hashCodeFormated(sgb4), Tools.hashCodeFormated(sgb5));
		report.add(message);
		report.add(Constants.LINE);
		return "";
	}

	/**
	 * Researches stateful beans.
	 * 
	 * @return the result
	 */
	public String researchStateful() {

		submitTasks(new Common[] { sfb1, sfb2, sfb3, sfb4, sfb5 }, "*** Stateful session beans: ");
		final String message = String.format(
				"*** Stateful session beans injected objects hash codes: sfb1[%s], sfb2[%s], sfb3[%s], sfb4[%s], sfb5[%s]",
				Tools.hashCodeFormated(sfb1), Tools.hashCodeFormated(sfb2), Tools.hashCodeFormated(sfb3),
				Tools.hashCodeFormated(sfb4), Tools.hashCodeFormated(sfb5));
		report.add(message);
		report.add(Constants.LINE);
		return "";
	}

	/**
	 * Submits tasks.
	 * 
	 * @param sbArr the session beans array
	 * @param label the label
	 */
	private void submitTasks(Common[] sbArr, String label) {

		final List<String> list = Collections.synchronizedList(new ArrayList<>());
		final String stamp = TEXT_LIST.get(0);
		Collections.rotate(TEXT_LIST, -1);

		executorService.submit(() -> list.add(sbArr[0].change(stamp, 1)));
		executorService.submit(() -> list.add(sbArr[1].change(stamp, 2)));
		executorService.submit(() -> list.add(sbArr[2].change(stamp, 3)));
		executorService.submit(() -> list.add(sbArr[3].change(stamp, 4)));
		executorService.submit(() -> list.add(sbArr[4].change(stamp, 5)));

		addToReport(label, list);
	}

	/**
	 * Adds to report.
	 * 
	 * @param label the label
	 * @param list  the list
	 */
	private void addToReport(String label, List<String> list) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// ignore
		}
		list.sort(null);
		report.add(label);
		list.stream().forEach(report::add);
	}
}