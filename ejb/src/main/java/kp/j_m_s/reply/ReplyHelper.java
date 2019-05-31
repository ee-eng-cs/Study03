package kp.j_m_s.reply;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * The reply helper.
 *
 */
public class ReplyHelper {

	@Inject
	private List<String> report;

	@Inject
	private JMSContext context;

	/**
	 * Processes the control message.
	 * 
	 * @param domain  the domain
	 * @param message the message
	 * @throws JMSException the JMS exception
	 */
	public void processControlMessage(Message message, String domain) throws JMSException {

		final String label = message.getStringProperty("label");
		report.add(String.format("'%s' received CONTROL message, label[%s]", domain, label));

		final Queue replyQueue = (Queue) message.getJMSReplyTo();
		final Message replyMessage = context.createMessage();
		replyMessage.setStringProperty("label", label);
		context.createProducer().send(replyQueue, replyMessage);
		report.add(String.format("'reply queue' sent CONTROL message, label[%s]", label));
	}
}