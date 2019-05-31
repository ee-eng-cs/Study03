package kp.j_m_s.sync.topic;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Topic;

import kp.j_m_s.sync.ConsumerSync;

/**
 * JMS messages synchronous topic consumer.
 *
 */
@Named
@RequestScoped
public class TopicConsumerSync extends ConsumerSync {
	@Inject
	private Logger logger;

	@Resource(lookup = "jms/StudyTopicSync")
	private Topic topic;

	/**
	 * Receives topic messages.
	 * 
	 * @return the result
	 */
	public String receiveTopicMessages() {
		receiveMessages(topic, "topic");
		logger.info("receiveTopicMessages():");
		return "";
	}
}