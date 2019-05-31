package kp.trans_c_m_t.controller;

import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CONFIRM_CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.READ;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import kp.trans_c_m_t.queues.ParcelQueueQualifier;
import kp.trans_c_m_t.service.AuditorBean;

/**
 * CDI managed bean for container-managed transactions research.
 *
 */
@Named
@RequestScoped
public class TransCmtManagedBean {
	@Inject
	private Logger logger;

	@Inject
	private JMSContext context;

	@Inject
	@ParcelQueueQualifier(CREATE)
	private Queue createParcelQueue;

	@Inject
	@ParcelQueueQualifier(CONFIRM_CREATE)
	private Queue confirmCreateParcelQueue;

	@Inject
	@ParcelQueueQualifier(READ)
	private Queue readParcelQueue;

	@Inject
	private AuditorBean auditorBean;

	private static final List<String> TEXT_LIST = IntStream.concat(/*-*/
			IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)), /*-*/
			IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))/*-*/
			.mapToObj(num -> Character.toString((char) num)).collect(Collectors.toList());

	/**
	 * Creates the parcel.
	 * 
	 * @return the result
	 */
	public String create() {

		final Message message = context.createTextMessage(TEXT_LIST.get(0));
		try {
			message.setJMSReplyTo(confirmCreateParcelQueue);
		} catch (JMSException e) {
			logger.severe(String.format("create(): exception[%s]", e.getMessage()));
			return "";
		}
		context.createProducer().send(createParcelQueue, message);
		Collections.rotate(TEXT_LIST, -1);
		logger.info("create():");
		return "";
	}

	/**
	 * Reads the parcel.
	 * 
	 * @return the result
	 */
	public String read() {
		context.createProducer().send(readParcelQueue, context.createMessage());
		logger.info("read():");
		return "";
	}

	/**
	 * Shows audit.
	 * 
	 * @return the result
	 */
	public String showAudit() {
		auditorBean.showAudit();
		logger.info("showAudit():");
		return "";
	}
}