package kp.e_j_b;

import java.util.logging.Logger;

import javax.inject.Inject;

import kp.util.Tools;

/**
 * The abstract implementation of the common interface.
 *
 */
abstract public class CommonImpl implements Common {

	private int previous = 0;

	@Inject
	private Logger logger;

	/**
	 * {@inheritDoc} Implemented.
	 * 
	 */
	@Override
	public String check(String stamp) {

		final String message = String.format("implementation[%s], stamp[%s]", this.getClass().getSimpleName(), stamp);
		logger.info(String.format("check(): %s", message));
		return message;
	}

	/**
	 * {@inheritDoc} Implemented.
	 * 
	 */
	@Override
	public String change(String stamp, int number) {

		final String message = String.format(
				"stamp[%s], current number[%s], previous number[%s], current equals previous[%b], object hash code[%s]",
				stamp, number, previous, number == previous, Tools.hashCodeFormated(this));
		previous = number;
		logger.info(String.format("change(): %s", message));
		return message;
	}
}