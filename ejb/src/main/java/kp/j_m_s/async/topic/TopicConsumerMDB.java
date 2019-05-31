package kp.j_m_s.async.topic;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import kp.j_m_s.async.ConsumerAsync;

/**
 * The Message-Driven Bean servicing topic.
 * 
 */
@MessageDriven(activationConfig = { /*-*/
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyTopicAsync"), /*-*/
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class TopicConsumerMDB extends ConsumerAsync implements MessageListener {
	@Inject
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void onMessage(Message message) {
		process(message, "topic");
		logger.info("onMessage():");
	}
}