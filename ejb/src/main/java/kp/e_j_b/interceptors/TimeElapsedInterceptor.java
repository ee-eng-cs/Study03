package kp.e_j_b.interceptors;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kp.util.Tools;

/**
 * Time Elapsed Interceptor.
 *
 */
public class TimeElapsedInterceptor {
	/*-
	Interceptor functionality is defined in the Java Interceptors specification.
	
	CDI enhances this functionality. But CDI-style interceptor is not researched here
	because it needs enabling in the 'beans.xml'.
	*/

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	private static final int PAUSE = 1;

	/**
	 * Intercepting method.
	 * 
	 * @param invocationContext the invocationContext
	 * @return the result
	 */
	@AroundInvoke
	public Object reportTimeElapsed(InvocationContext invocationContext) {

		Object result = null;
		final long start = System.nanoTime();
		try {
			result = invocationContext.proceed();
		} catch (Exception e) {
			// ignore
		}
		final long diff = System.nanoTime() - start;
		final String msg = String.format("Method[%s], time elapsed [%d]ns (reference), [%s]ns (after invoke)",
				invocationContext.getMethod().getName(), getReference(), Tools.formatNumber(diff));
		report.add(msg);
		logger.info("reportTimeElapsed():");
		return result;
	}

	/**
	 * Gets reference measure.
	 * 
	 * @return the message
	 */
	private long getReference() {

		long start = System.nanoTime(), diff;
		do {
			diff = System.nanoTime() - start;
		} while (diff < PAUSE);
		return diff;
	}
}