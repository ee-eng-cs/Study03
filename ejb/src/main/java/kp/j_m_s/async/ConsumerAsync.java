package kp.j_m_s.async;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import kp.j_m_s.reply.ReplyHelper;

/**
 * The asynchronous consumer base.
 *
 */
public abstract class ConsumerAsync {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private ReplyHelper replyHelper;

	/**
	 * Processes messages.
	 * 
	 * @param message the message
	 * @param domain  the domain
	 */
	protected void process(Message message, String domain) {

		try {
			if (message instanceof TextMessage) {
				String text = message.getBody(String.class);
				report.add(String.format("'%s' received message[%s]", domain, text));
			} else {
				replyHelper.processControlMessage(message, domain);
			}
		} catch (JMSException e) {
			logger.severe(String.format("process(): JMSException[%s]", e.getMessage()));
			return;
		}
	}
}