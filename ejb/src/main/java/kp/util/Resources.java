package kp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The resources.
 *
 */
public class Resources {
	/**
	 * Produces logger.
	 * 
	 * @param injectionPoint the injectionPoint
	 * @return the logger
	 */
	@Produces
	public Logger getLogger(InjectionPoint injectionPoint) {
		final String category = injectionPoint.getMember().getDeclaringClass().getName();
		return Logger.getLogger(category);
	}

	/**
	 * Produces report.
	 */
	@Named
	@Produces
	public static final List<String> report = new ArrayList<>();

	/**
	 * Produces entity manager.
	 */
	@PersistenceContext
	@Produces
	private EntityManager entityManager;
}