package kp.j_m_s.sync.queue;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Queue;

import kp.j_m_s.Producer;

/**
 * JMS messages queue producer (for synchronous consumer).
 *
 */
@Named
@RequestScoped
public class QueueProducerSync extends Producer {
	@Inject
	private Logger logger;

	@Resource(lookup = "jms/StudyQueueSync")
	private Queue queue;

	/**
	 * Sends queue messages.
	 * 
	 * @return the result
	 */
	public String sendQueueMessages() {
		sendMessages(queue, "queue");
		logger.info("sendQueueMessages():");
		return "";
	}
}