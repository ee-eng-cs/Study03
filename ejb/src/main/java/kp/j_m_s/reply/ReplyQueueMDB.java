package kp.j_m_s.reply;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * The Message-Driven Bean servicing reply queue.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyReplyQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class ReplyQueueMDB implements MessageListener {
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

		String label = null;
		try {
			label = message.getStringProperty("label");
		} catch (JMSException e) {
			logger.severe(String.format("onMessage(): JMSException[%s]", e.getMessage()));
			return;
		}
		report.add(String.format("'reply queue' received CONTROL message, label[%s]", label));
		logger.info(String.format("onMessage(): label[%s]", label));
	}
}