package kp.e_j_b.local;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Stateless Session Bean.<BR>
 * This is Local, No-Interface View Enterprise Bean.<BR>
 * <BR>
 * Because this enterprise bean class doesn’t implement a business
 * interface,<BR>
 * the enterprise bean exposes a local, no-interface view.<BR>
 * <BR>
 * (A business interface is not required if the enterprise bean exposes a local,
 * no-interface view.)
 */
@Stateless
public class NoIntViBean {

	@Inject
	private Logger logger;

	/**
	 * Checks the bean implementation.<BR>
	 * Business method.
	 * 
	 * @param stamp the stamp
	 * @return message the message
	 */
	public String check(String stamp) {

		final String message = String.format("implementation[%s], stamp[%s], no-interface view stateless bean",
				this.getClass().getSimpleName(), stamp);
		logger.info(String.format("check(): %s", message));
		return message;
	}
}