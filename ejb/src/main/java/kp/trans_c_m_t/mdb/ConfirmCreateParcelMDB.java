package kp.trans_c_m_t.mdb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * The Message-Driven Bean for <B>parcel</B> object creation confirmation.
 * 
 */
@MessageDriven(activationConfig = { /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationLookup", propertyValue = "jms/ConfirmCreateParcelQueue"), /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class ConfirmCreateParcelMDB implements MessageListener {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void onMessage(Message message) {

		String text = null;
		try {
			text = ((TextMessage) message).getText();
		} catch (JMSException e) {
			logger.severe(String.format("onMessage(): exception[%s]", e.getMessage()));
			return;
		}
		report.add(String.format("ConfirmCreateParcelMDB received text[%s] from 'ConfirmCreateParcelQueue'.", text));
		logger.info(String.format("onMessage(): text[%s]", text));
	}
}