package kp.trans_c_m_t.mdb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import kp.trans_c_m_t.helper.Approver;
import kp.trans_c_m_t.service.ParcelAdministratorBean;

/**
 * The Message-Driven Bean for <B>parcel</B> object creation.
 * 
 */
@MessageDriven(activationConfig = { /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationLookup", propertyValue = "jms/CreateParcelQueue"), /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class CreateParcelMDB implements MessageListener {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	ParcelAdministratorBean parcelAdministratorBean;

	@Inject
	Approver approver;

	@Inject
	private JMSContext context;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void onMessage(Message message) {

		String text = null;
		try {
			text = ((TextMessage) message).getText();
		} catch (JMSException e) {
			logger.severe(String.format("onMessage(): exception[%s]", e.getMessage()));
			return;
		}
		report.add(String.format("CreateParcelMDB received text[%s] from 'CreateParcelQueue'.", text));
		int parcelId = parcelAdministratorBean.create(text);
		sendConfirmMessage(message, parcelId);
		// last step in current transaction
		approver.approve(parcelId);
		logger.info(String.format("onMessage(): text[%s], parcelId[%d]", text, parcelId));
	}

	/**
	 * Sends confirm message.
	 * 
	 * @param message  the message
	 * @param parcelId the parcel id
	 */
	private void sendConfirmMessage(Message message, int parcelId) {

		String text = null;
		try {
			final Queue confirmQueue = (Queue) message.getJMSReplyTo();
			text = String.format("%s:%d", ((TextMessage) message).getText(), parcelId);
			final Message confirmMessage = context.createTextMessage(text);
			context.createProducer().send(confirmQueue, confirmMessage);
		} catch (JMSException e) {
			logger.severe(String.format("sendConfirmMessage(): exception[%s]", e.getMessage()));
			return;
		}
		report.add(String.format("CreateParcelMDB sent confirm message with text[%s] to 'ConfirmCreateParcelQueue'",
				text));
	}
}