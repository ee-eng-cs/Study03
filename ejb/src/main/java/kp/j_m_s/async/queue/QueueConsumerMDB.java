package kp.j_m_s.async.queue;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import kp.j_m_s.async.ConsumerAsync;

/**
 * The Message-Driven Bean servicing queue.
 * 
 */
@MessageDriven(activationConfig = { /*-*/
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyQueueAsync"), /*-*/
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class QueueConsumerMDB extends ConsumerAsync implements MessageListener {
	@Inject
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void onMessage(Message message) {
		process(message, "queue");
		logger.info("onMessage():");
	}
}