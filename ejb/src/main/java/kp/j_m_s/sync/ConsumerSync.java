package kp.j_m_s.sync;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import kp.j_m_s.reply.ReplyHelper;

/**
 * The synchronous consumer base.
 *
 */
public abstract class ConsumerSync {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private JMSContext context;

	@Inject
	private ReplyHelper replyHelper;

	/**
	 * Receives messages.
	 * 
	 * @param destination the destination
	 * @param domain      the domain
	 */
	protected void receiveMessages(Destination destination, String domain) {

		try {
			final JMSConsumer consumer = context.createConsumer(destination);
			while (true) {
				final Message message = consumer.receive(1000);
				if (Objects.isNull(message)) {
					continue;
				}
				if (message instanceof TextMessage) {
					String text = message.getBody(String.class);
					report.add(String.format("'%s' received message[%s]", domain, text));
				} else {
					replyHelper.processControlMessage(message, domain);
					break;
				}
			}
		} catch (JMSException e) {
			logger.severe(String.format("receiveMessages(): domain[%s], exception[%s]", domain, e.getMessage()));
			return;
		}
	}
}