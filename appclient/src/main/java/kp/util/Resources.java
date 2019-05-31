package kp.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * The resources.
 *
 */
public class Resources {
	/**
	 * Produces logger.
	 * 
	 * @param injectionPoint the injection point
	 * @return logger the logger
	 */
	@Produces
	public Logger getLogger(InjectionPoint injectionPoint) {
		final String category = injectionPoint.getMember().getDeclaringClass().getName();
		return Logger.getLogger(category);
	}
}