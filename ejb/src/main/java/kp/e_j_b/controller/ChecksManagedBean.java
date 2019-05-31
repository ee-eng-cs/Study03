package kp.e_j_b.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import kp.Constants;
import kp.e_j_b.local.NoIntViBean;
import kp.e_j_b.local.StaLeLocal;
import kp.e_j_b.remote.Sing;
import kp.e_j_b.remote.StaFu;
import kp.e_j_b.remote.StaLe;

/**
 * CDI Managed Bean for EJBs Checks.
 *
 */
@Named
@RequestScoped
public class ChecksManagedBean {
	/*-
	As from JSF 2.3 onwards '@ManagedBean' (bean managed by JSF container) is being phased out and
	the recommended approach is '@Named' (CDI bean managed by application server).
	*/

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@EJB
	private NoIntViBean noIntViBean, noIntViBeanToCompare;
	@Inject
	private NoIntViBean noIntViBeanInjected, noIntViBeanInjectedToCompare;

	@EJB
	private StaLeLocal staLeLocal, staLeLocalToCompare;
	@Inject
	private StaLeLocal staLeLocalInjected, staLeLocalInjectedToCompare;

	/*-
	You can't inject an EJB using its remote interface unless you define a resource.
	It raises DeploymentException "Unsatisfied dependencies" when using annotation @Inject:
	 - with stateless bean remote view
	 - with stateful  bean remote view
	 - with singleton  bean
	 */
	@EJB
	private StaLe staLe, staLeToCompare;

	@EJB
	private StaFu staFu, staFuToCompare;

	@EJB
	private Sing sing, singToCompare;

	/**
	 * Researches stateless, stateful, and singleton beans.
	 * 
	 * @return the result
	 */
	public String researchStatelessStatefulSingleton() {

		report.add(noIntViBean.check("from @EJB"));
		report.add(staLeLocal.check("from @EJB"));
		report.add(staLe.check("from @EJB"));
		report.add(staFu.check("from @EJB"));
		report.add(sing.check("from @EJB"));
		compareBeansFromEjb();
		report.add(Constants.LINE);

		report.add(noIntViBeanInjected.check("from @Inject"));
		report.add(staLeLocalInjected.check("from @Inject"));
		compareBeansFromInject();
		compareBeans();
		report.add(Constants.LINE);
		logger.info("researchStatelessStatefulSingleton():");
		return "";
	}

	/**
	 * Compares beans from @EJB.
	 * 
	 */
	private void compareBeansFromEjb() {

		String msg = String.format(
				"Compare two beans from '@EJB' injection: no-interface view[%b], stateless local view[%b], "
						+ "stateless remote view[%b], singleton remote view[%b]",
				noIntViBean.equals(noIntViBeanToCompare), staLeLocal.equals(staLeLocalToCompare),
				staLe.equals(staLeToCompare), sing.equals(singToCompare));
		report.add(msg);

		StaLeLocal staLeLocalFromLookup = null;
		StaLe staLeFromLookup = null;
		try {
			final InitialContext context = new InitialContext();
			staLeLocalFromLookup = (StaLeLocal) context.lookup("java:global/Study03/Study03_ejb/StaLeLocalBean");
			staLeFromLookup = (StaLe) context.lookup("java:global/Study03/Study03_ejb/StaLeBean");
		} catch (NamingException e) {
			e.printStackTrace();
			logger.severe(String.format("compareBeansFromEjb(): exception[%s]", e.getMessage()));
			return;
		}
		msg = String.format(
				"Compare bean from lookup vs bean from '@EJB' injection: stateless local view[%b], "
						+ "stateless remote view[%b]",
				staLeLocalFromLookup.equals(staLeLocalToCompare), staLeFromLookup.equals(staLeToCompare));
		report.add(msg);

		msg = String.format("Compare two beans from '@EJB' injection: stateful remote view[%b]",
				staFu.equals(staFuToCompare));
		report.add(msg);
	}

	/**
	 * Compares beans from @Inject.
	 * 
	 */
	private void compareBeansFromInject() {

		final String msg = String.format(
				"Compare two beans from '@Inject' injection: no-interface view[%b], stateless local view[%b]",
				noIntViBeanInjected.equals(noIntViBeanInjectedToCompare),
				staLeLocalInjected.equals(staLeLocalInjectedToCompare));
		report.add(msg);
	}

	/**
	 * Compares beans from @EJB and from @Inject.
	 * 
	 */
	private void compareBeans() {

		final String msg = String.format(
				"Compare bean from '@EJB' injection vs bean from '@Inject' injection: "
						+ "no-interface view[%b], stateless local view[%b]",
				noIntViBean.equals(noIntViBeanInjected), staLeLocal.equals(staLeLocalInjected));
		report.add(msg);
	}
}