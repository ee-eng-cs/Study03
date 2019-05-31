package kp.j_m_s.sync.queue;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Queue;

import kp.j_m_s.sync.ConsumerSync;

/**
 * JMS messages synchronous queue consumer.
 *
 */
@Named
@RequestScoped
public class QueueConsumerSync extends ConsumerSync {
	@Inject
	private Logger logger;

	@Resource(lookup = "jms/StudyQueueSync")
	private Queue queue;

	/**
	 * Receives queue messages.
	 * 
	 * @return the result
	 */
	public String receiveQueueMessages() {
		receiveMessages(queue, "queue");
		logger.info("receiveQueueMessages():");
		return "";
	}
}