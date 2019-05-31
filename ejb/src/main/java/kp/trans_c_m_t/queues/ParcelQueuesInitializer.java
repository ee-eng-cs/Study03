package kp.trans_c_m_t.queues;

import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CONFIRM_CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.READ;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;

/**
 * The queues initializer for the message-driven beans.
 * 
 */
@JMSDestinationDefinitions({ /*-*/
		@JMSDestinationDefinition( /*-*/
				name = "java:/jms/CreateParcelQueue", /*-*/
				destinationName = "CreateParcelQueue", interfaceName = "javax.jms.Queue"),
		@JMSDestinationDefinition( /*-*/
				name = "java:/jms/ConfirmCreateParcelQueue", /*-*/
				destinationName = "ConfirmCreateParcelQueue", interfaceName = "javax.jms.Queue"),
		@JMSDestinationDefinition( /*-*/
				name = "java:/jms/ReadParcelQueue", /*-*/
				destinationName = "ReadParcelQueue", interfaceName = "javax.jms.Queue") })
@Startup
@Singleton
public class ParcelQueuesInitializer {

	@Resource(lookup = "java:/jms/CreateParcelQueue")
	@Produces
	@ParcelQueueQualifier(CREATE)
	public static Queue createParcelQueue;

	@Resource(lookup = "java:/jms/ConfirmCreateParcelQueue")
	@Produces
	@ParcelQueueQualifier(CONFIRM_CREATE)
	public static Queue confirmCreateParcelQueue;

	@Resource(lookup = "java:/jms/ReadParcelQueue")
	@Produces
	@ParcelQueueQualifier(READ)
	public static Queue readParcelQueue;
}
/*-
 These three queues above are created without using any CLI commands.
 
 An example of the CLI command for listing:
 ls /subsystem=messaging-activemq/server=default/runtime-queue=jms.queue.CreateParcelQueue/
*/