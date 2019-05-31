package kp.client;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import kp.Constants;
import kp.e_j_b.remote.Sing;
import kp.e_j_b.remote.StaFu;
import kp.e_j_b.remote.StaLe;

/**
 * The application for JBoss Application Client.
 *
 */
public class MainClient {

	@EJB
	private static StaLe staLe;

	@EJB
	private static StaFu staFu;

	@EJB
	private static Sing sing;

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(Constants.BREAK);
		System.out.println(staLe.check("from @EJB"));
		/*- 
		   When tried with 'StaLeLocalBean' got EJBException:
		   "Server error (invalid view): EJB view is not remote".
		*/
		final StaLe staLeFromLookup = (StaLe) jndiLookup("StaLeBean");
		System.out.println(staLeFromLookup.check("from lookup"));
		System.out.println(staFu.check("from @EJB"));
		System.out.println(sing.check("from @EJB"));
		System.out.println(Constants.BREAK);
	}

	/**
	 * The JNDI lookup.
	 * 
	 * @param name the name of the object to look up
	 * @return the object bound to name
	 */
	private static Object jndiLookup(String name) {

		Object object = null;
		/*- java : global / <app-name> / <module-name> / <bean-name> ! <fully-qualified-interface-name> */
		try {
			object = new InitialContext().lookup(String.format("java:global/Study03/Study03_ejb/%s", name));
		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return object;
	}
}