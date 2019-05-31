package kp.j_m_s;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * JMS messages producer base.
 *
 */
public abstract class Producer {
	/*-
	 * A 'javax.jms.Destination' object is a JMS administered object.
	 * 
	 * The 'Destination' is the target of messages that the client produces.
	 * The 'Destination' is the source of messages that the client consumes.
	 * 
	 * The 'Destination' is the JMS queue in the point-to-point    messaging domain.
	 * The 'Destination' is the JMS topic in the publish/subscribe messaging domain.
	 */

	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	/*-
	 * Injected JMSContext is container-managed.
	 * Therefore these methods must not be used:
	 * 'commit', 'rollback', 'acknowledge', 'start', 'stop', 'recover', 'close'. 
	 */
	@Inject
	private JMSContext context;

	@Resource(lookup = "jms/StudyReplyQueue")
	private Queue replyQueue;

	private static final List<String> TEXT_LIST = IntStream.concat(/*-*/
			IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)), /*-*/
			IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))/*-*/
			.mapToObj(num -> Character.toString((char) num)).collect(Collectors.toList());

	/**
	 * Sends messages.
	 * 
	 * @param destination the destination
	 * @param domain      the domain
	 */
	protected void sendMessages(Destination destination, String domain) {

		StringBuilder strBld = new StringBuilder();
		try {
			final JMSProducer producer = context.createProducer();
			for (int i = 1; i <= 3; i++) {
				final String text = TEXT_LIST.get(0);
				Collections.rotate(TEXT_LIST, -1);
				producer.send(destination, text);
				report.add(String.format("'%s' sent message[%s]", domain, text));
				strBld.append(text);
			}
			/*
			 * Send a non-text control message indicating end of messages.
			 */
			final Message controlMessage = context.createMessage();
			final String label = strBld.toString();
			controlMessage.setStringProperty("label", label);
			controlMessage.setJMSReplyTo(replyQueue);
			producer.send(destination, controlMessage);
			report.add(String.format("'%s' sent CONTROL message, label[%s]", domain, label));
		} catch (JMSRuntimeException | JMSException e) {
			logger.severe(String.format("sendMessages(): domain[%s], exception[%s]", domain, e.getMessage()));
			return;
		}
	}
}