package kp.trans_c_m_t.helper;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import kp.trans_c_m_t.service.AuditorBean;

/**
 * The <B>approver</B>.
 *
 */
@Stateless
public class Approver {
	@Inject
	private Logger logger;

	@Inject
	private List<String> report;

	@Inject
	private AuditorBean auditorBean;

	@Resource
	private EJBContext ejbContext;

	/**
	 * Approves only the parcel id with even value.<BR>
	 * Rollbacks every parcel id with odd value.
	 * 
	 * @param parcelId the parcel id
	 */
	@Transactional
	public void approve(int parcelId) {

		final String msg;
		if (parcelId % 2 == 1) {
			ejbContext.setRollbackOnly();
			msg = String.format("The current transaction is marked for rollback, parcelId[%d].", parcelId);
			report.add(msg);
		} else {
			auditorBean.recordApproved();
			msg = "OK";
		}
		logger.info(String.format("approve(): %s", msg));
	}
}