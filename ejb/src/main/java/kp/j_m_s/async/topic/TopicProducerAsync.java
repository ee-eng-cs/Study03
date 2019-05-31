package kp.j_m_s.async.topic;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Topic;

import kp.j_m_s.Producer;

/**
 * JMS messages topic producer (for asynchronous consumer).
 *
 */
@Named
@RequestScoped
public class TopicProducerAsync extends Producer {
	@Inject
	private Logger logger;

	@Resource(lookup = "jms/StudyTopicAsync")
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