package kp.e_j_b.controller;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kp.e_j_b.interceptors.TimeElapsedBean;

/**
 * CDI Managed Bean for Interceptors.
 *
 */
@Named
@RequestScoped
public class InterceptorsManagedBean {
	@Inject
	private Logger logger;

	@EJB
	private static TimeElapsedBean timeElapsedBean;

	/**
	 * Researches interceptor.
	 * 
	 * @return the result
	 */
	public String researchInterceptor() {

		timeElapsedBean.pausedMilli();
		timeElapsedBean.pausedNano();
		timeElapsedBean.notPaused();
		logger.info("researchInterceptor():");
		return "";
	}
}