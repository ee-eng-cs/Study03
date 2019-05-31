package kp.trans_c_m_t.mdb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import kp.trans_c_m_t.service.ParcelAdministratorBean;

/**
 * The Message-Driven Bean for <B>parcel</B> objects list reading.
 * 
 */
@MessageDriven(activationConfig = { /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationLookup", propertyValue = "jms/ReadParcelQueue"), /*-*/
		@ActivationConfigProperty( /*-*/
				propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class ReadParcelMDB implements MessageListener {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	ParcelAdministratorBean parcelAdministratorBean;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void onMessage(Message message) {

		report.add("ReadParcelMDB received message from 'ReadParcelQueue'.");
		parcelAdministratorBean.read();
		logger.info("onMessage():");
	}
}