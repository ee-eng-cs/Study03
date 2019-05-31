package kp.j_m_s.sync.topic;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Topic;

import kp.j_m_s.Producer;

/**
 * JMS messages topic producer (for synchronous consumer).
 *
 */
@Named
@RequestScoped
public class TopicProducerSync extends Producer {
	@Inject
	private Logger logger;

	@Resource(lookup = "jms/StudyTopicSync")
	private Topic topic;

	/**
	 * Sends topic messages.
	 * 
	 * @return the result
	 */
	public String sendTopicMessages() {
		sendMessages(topic, "topic");
		logger.info("sendTopicMessages():");
		return "";
	}
}