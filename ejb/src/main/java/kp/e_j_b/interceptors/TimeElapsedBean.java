package kp.e_j_b.interceptors;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * Bean with method invocation Interceptor.
 *
 */
@Stateless
public class TimeElapsedBean {
	private static final int PAUSE_NANO = 1;
	private static final int PAUSE_MILLI = 1_000_000;

	/**
	 * Method not paused.
	 * 
	 */
	@Interceptors(TimeElapsedInterceptor.class)
	public void notPaused() {
	}

	/**
	 * Method with nanosecond pause.
	 * 
	 */
	@Interceptors(TimeElapsedInterceptor.class)
	public void pausedNano() {
		long start = System.nanoTime(), diff;
		do {
			diff = System.nanoTime() - start;
		} while (diff < PAUSE_NANO);
	}

	/**
	 * Method with millisecond pause.
	 * 
	 */
	@Interceptors(TimeElapsedInterceptor.class)
	public void pausedMilli() {
		long start = System.nanoTime(), diff;
		do {
			diff = System.nanoTime() - start;
		} while (diff < PAUSE_MILLI);
	}
}