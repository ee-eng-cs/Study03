<!DOCTYPE html>
<HTML>
<HEAD>
	<META charset="UTF-8">
</HEAD>
<BODY>
<IMG src="images/ColorScheme.png" height="25" width="800"/>
<H2 id="contents">Study03 README Contents</H2>
<OL>
	<LI><A href="#e_j_b">Research Enterprise JavaBeans</A><UL>
		<LI><A href="#e_j_b_tasks">EJB Example 'Tasks'</A></LI>
		<LI><A href="#e_j_b_checks">EJB Example 'Checks'</A></LI>
		<LI><A href="#e_j_b_interceptor">EJB Example 'Interceptor'</A></LI>
	</UL></LI>
	<LI><A href="#j_p_a">Research Java Persistence API</A><UL>
		<LI><A href="#j_p_a_boxes">JPA Example 'Boxes'</A> (<I>one-to-one</I>, <I>one-to-many</I>, <I>many-to-one</I>, and <I>many-to-many</I> relationships)</LI>
		<LI><A href="#j_p_a_units">JPA Example 'Units'</A> (<I>previous-next</I> and <I>parent-child</I> self-referential relationships)</LI>
		<LI><A href="#j_p_a_levels">JPA Example 'Levels'</A> (hierarchical multilevel relationships)</LI>
	</UL></LI>
	<LI><A href="#j_m_s">Research Java Message Service</A><UL>
	  <LI><A href="#j_m_s_sync">JMS Example 'Synchronous Queue &amp; Topic'</A></LI>
	  <LI><A href="#j_m_s_async">JMS Example 'Asynchronous Queue &amp; Topic'</A></LI>
	</UL></LI>
	<LI><A href="#trans_c_m_t">Research Container-Managed Transactions</A></LI>
	<LI><A href="#trans_b_m_t">Research Bean-Managed Transactions</A></LI>
</OL>

<H2 id="e_j_b">❶ Research Enterprise JavaBeans</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/common/src/main/java/kp/e_j_b/">kp.e_j_b</a> in 'common' module<BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/ejb/src/main/java/kp/e_j_b/">kp.e_j_b</a> in 'ejb' module<BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/appclient/src/main/java/kp/client/">kp.client</a> in 'appclient' module.<BR/>
<img src="images/aquaHR-500.png"></P>

<P>Initial action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Go to page <a href="http://localhost:8080/Study03/">http://localhost:8080/Study03/</a> 
	and select "Research Enterprise JavaBeans".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/e_j_b_Controls.png" width="1200" /><BR/>
<I>Screen from "Research Enterprise JavaBeans" page controls.</I></P>

<HR/>
<H3 id="e_j_b_tasks">EJB Example 'Tasks'</H3>

<OL>There are three batches (each having five tasks) launched with managed executor service:
  <LI>The batch with tasks using stateless session beans.</LI>
  <LI>The batch with tasks using singleton session beans.</LI>
  <LI>The batch with tasks using stateful session beans.</LI>
</OL>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Tasks With Stateless" three times.</BR>
<img src="images/orangeHR-500.png"></P>

<OL>Results:
  <LI>It is evident that the bean stateless state (the value of the bean field) as a rule is not preserved.</LI>
  <LI>All five (injected with @EJB) stateless beans reference the same proxy object (hash codes are equal).</LI>
  <LI>All five stateless bean instances, used in launched tasks, were different objects (hash codes are not equal).</LI>
</OL>

<P><IMG src="images/TasksWithStateless.png" height="564" width="1014"/><BR>
<I>Screen from three 'Tasks With Stateless' actions.</I></P>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Tasks With Singleton" three times.</BR>
<img src="images/orangeHR-500.png"></P>

<OL>Results:
  <LI>From the bean hash code it is evident that the singleton bean state (the value of the bean field) is always overwritten.</LI>
  <LI>All five (injected with @EJB) singleton beans reference the same proxy object (hash codes are equal).</LI>
  <LI>All five singleton bean instances, used in launched tasks, were the same object (hash codes are equal).</LI>
</OL>

<P><IMG src="images/TasksWithSingleton.png" height="565" width="1056"/><BR>
<I>Screen from three 'Tasks With Singleton' actions.</I></P>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Tasks With Stateful" three times.</BR>
<img src="images/orangeHR-500.png"></P>

<OL>Results:
  <LI>The bean state (the value of the bean field) is always preserved.</LI>
  <LI>All five (injected with @EJB) stateful beans reference different proxy objects (hash codes are not equal).</LI>
  <LI>All five stateful bean instances, used in launched tasks, were different objects (hash codes are not equal).</LI>
</OL>

<P><IMG src="images/TasksWithStateful.png" height="563" width="1022"/><BR>
<I>Screen from three 'Tasks With Stateful' actions.</I></P>

<HR/>

<H3 id="e_j_b_checks">EJB Example 'Checks'</H3>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Check Stateless &amp; Stateful &amp; Singleton".</BR>
<img src="images/orangeHR-500.png"></P>

<OL>The bean instances invoked in this example:
  <LI>Local, no-interface view enterprise bean; injected with '@EJB' annotation; with class name 'NoIntViBean'.</LI>
  <LI>Local, stateless enterprise bean; injected with '@EJB' annotation; with class name 'StaLeLocalBean'.</LI>
  <LI>Remote, stateless enterprise bean; injected with '@EJB' annotation; with class name 'StaLeBean'.</LI>
  <LI>Remote, stateful enterprise bean; injected with '@EJB' annotation; with class name 'StaFuBean'.</LI>
  <LI>Remote, singleton enterprise bean; injected with '@EJB' annotation; with class name 'SingBean'.</LI>
  <LI>Local, no-interface view enterprise bean; injected with '@Inject' annotation; with class name 'NoIntViBean'.</LI>
  <LI>Local, stateless enterprise bean; injected with '@Inject' annotation; with class name 'StaLeLocalBean'.</LI>
</OL>

<P><IMG src="images/CheckStatelessStatefulSingleton.png" height="334" width="977"/><BR>
<I>Screen from 'Check Stateless &amp; Stateful &amp; Singleton' action.</I></P>

<HR/>

<H3 id="e_j_b_interceptor">EJB Example 'Interceptor'</H3>

There were implemented the interceptors for the time elapsed capture.
<OL>In 'TimeElapsedBean' class the interceptors were added to those methods:
  <LI>'pausedMilli()' - this method stops for one millisecond.</LI>
  <LI>'pausedNano()' - this method stops for one nanosecond.</LI>
  <LI>'notPaused()' - this method is empty inside (has no body).</LI>
</OL>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Interceptor".</BR>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/Interceptor.png" height="79" width="552"/><BR>
<I>Screen from 'Interceptor' action.</I></P>

<HR/>

<H2 id="j_p_a">❷ Research Java Persistence API</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/ejb/src/main/java/kp/j_p_a/">kp.j_p_a</a>.<BR/>
<img src="images/aquaHR-500.png"></P>

Datasource 'Study03DS' uses H2 database with name 'study03' and in-memory mode.<BR/>
For CRUD actions there were used criteria queries. There is the switch in code to use named queries.<BR/>

<P>Initial action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Go to page <a href="http://localhost:8080/Study03/">http://localhost:8080/Study03/</a>
	and select "Research Java Persistence API".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/j_p_a_Controls.png" width="1200" /><BR/>
<I>Screen from "Research Java Persistence API" page controls.</I></P>

<HR/>
<H3 id="j_p_a_boxes">JPA Example 'Boxes'</H3>
For 'Boxes' the JSF page uses 'BoxBean'.<BR/>
<P><IMG src="images/DiagramBoxes.png" height="502" width="750"/><BR>
<I>'Boxes' relationships diagram.</I></P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Push button "Create Box" four times. Only 3 calls were successful.<BR/>
<img src="images/spacer-32.png">The 4th call failed (it was the transaction rollback) because allowable creation maximum is 3 'CentralBox' objects.<BR/>
<img src="images/orangeCircle.png"> 2. Push button "Read Boxes". Report shows 3 'CentralBox' objects and its dependencies.<BR/>
<img src="images/orangeCircle.png"> 3. Push button "Update & Read Boxes". The 'CentralBox' field "cardinalDirection" was changed.<BR/>
<img src="images/orangeCircle.png"> 4. Push button "Delete Box" four times.<BR/>
<img src="images/spacer-32.png">The 4th call failed because there were no more 'CentralBox' objects left after three "Delete Box" calls.<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/CreateBox1.png" height="173" width="801"/><BR>
<I>Screen from 1st 'Create Box' action.</I></P>

<P><IMG src="images/CreateBox2.png" height="196" width="800"/><BR>
<I>Screen from 2nd 'Create Box' action.</I></P>

<P><IMG src="images/CreateBox3.png" height="218" width="800"/><BR>
<I>Screen from 3rd 'Create Box' action.</I></P>

<P><IMG src="images/CreateBox4.png" height="35" width="646"/><BR>
<I>Screen from 4th 'Create Box' action.</I></P>

<P><IMG src="images/ReadBoxes.png" height="727" width="802"/><BR>
<I>Screen from 'Read Boxes' action.</I></P>

<P><IMG src="images/UpdateAndReadBoxes.png" height="725" width="802"/><BR>
<I>Screen from 'Update &amp; Read Boxes' action.</I></P>

<P><IMG src="images/DeleteBox1.png" height="35" width="627"/><BR>
<I>Screen from 1st 'Delete Boxes' action.</I></P>

<P><IMG src="images/DeleteBox2.png" height="35" width="627"/><BR>
<I>Screen from 2nd 'Delete Boxes' action.</I></P>

<P><IMG src="images/DeleteBox3.png" height="36" width="627"/><BR>
<I>Screen from 3rd 'Delete Boxes' action.</I></P>

<P><IMG src="images/DeleteBox4.png" height="60" width="627"/><BR>
<I>Screen from 4th 'Delete Boxes' action.</I></P>

<HR/>

<H3 id="j_p_a_units">JPA Example 'Units'</H3>
For 'Units' the JSF page uses 'UnitManagedBean'. For CRUD actions there were used criteria queries.<BR/>
<P>The 'Units' have relationships:
<UL>
	<LI><I>One-To-One</I> self-referential relationship between 'previous' and 'next'</LI>
	<LI><I>Many-To-One</I> self-referential relationship between 'parent' and 'children'</LI>
	<LI><I>One-To-Many</I> relationship between 'Unit' object and 4 'Side' objects using enumeration 'CardinalDirection'</LI>
</UL></P>
	
<P><IMG src="images/DiagramUnits.png" height="200" width="600"/><BR>
<I>Units relationships diagram.</I></P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Push button "Create-Read-Delete Units" the 1st time.<BR/>
<img src="images/spacer-32.png">The 'Units' were created and persisted to database.<BR/>
<img src="images/orangeCircle.png"> 2. Push button "Create-Read-Delete Units" the 2nd time.<BR/>
<img src="images/spacer-32.png">In that request the 'Units' were read from database, presented as a report and removed from database.<BR/>
<img src="images/orangeHR-500.png"></P>

<P>The presented report shows the results of the query.<BR/>
For 3 'Units' there is <I>previous - next</I> relationship : A -> B -> C.<BR/>
For 4 'Units' there is <I>parent - children</I> relationship: C -> (X, Y, Z).<BR/>
For all 6 'Units' and 24 'Sides' there is <I>one-to-many</I> relationship:<BR/>
<img src="images/spacer-32.png">every 'Unit' object has four unique 'Side' objects.</P>

<P><IMG src="images/CreateReadDeleteUnits1.png" height="105" width="282"/><BR>
<I>Screen from 1st 'Create-Read-Delete Units' action</I></P>

<P><IMG src="images/CreateReadDeleteUnits2.png" height="242" width="776"/><BR>
<I>Screen from 2nd 'Create-Read-Delete Units' action</I></P>

<HR/>

<H3 id="j_p_a_levels">JPA Example 'Levels'</H3>
For 'Levels' the JSF page uses 'LevelManagedBean'. For CRUD actions there were used criteria queries with metamodel.

<P><IMG src="images/DiagramLevels.png" height="350" width="200"/><BR>
<I>'Levels' relationships diagram. It is the 'One-To-Many' hierarchical relationships.</I></P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Push button "Create-Read-Delete Levels" the 1st time.<BR/>
<img src="images/spacer-32.png">The 'Levels' were created and persisted to database.<BR/>
<img src="images/orangeCircle.png"> 2. Push button "Create-Read-Delete Levels" the 2nd time.<BR/>
<img src="images/spacer-32.png">In that request the 'Levels' were read from database, presented as a report and removed from database.<BR/>
<img src="images/orangeHR-500.png"></P>

<P>The presented report shows the results of four queries.<BR/>
1st query using string with pattern "<B>SELECT ... FROM ... JOIN ... WHERE ... IN ...</B>".<BR/>
2nd query using string with pattern "<B>SELECT ... FROM ... IN   ... WHERE ... IN ...</B>".<BR/>
3rd query using criteria with '<B>join</B>'.<BR/>
4th query is the aggregate function query using criteria with '<B>groupBy</B>'.</P>

<P>For the 1st, 2nd and 3rd query the result is identical.<BR/>
The result of the 4th query is the intersection of the '<B>having</B>' restriction and the '<B>where</B>' restriction.</P>

<P><IMG src="images/CreateReadDeleteLevels1.png" height="57" width="107"/><BR>
<I>Screen from 1st 'Create-Read-Delete Levels' action</I></P>

<P><IMG src="images/CreateReadDeleteLevels2.png" height="703" width="938"/><BR>
<I>Screen from 2nd 'Create-Read-Delete Levels' action</I></P>

<HR/>

<H2 id="j_m_s">❸ Research Java Message Service</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/ejb/src/main/java/kp/j_m_s/">kp.j_m_s</a>.<BR/>
<img src="images/aquaHR-500.png"></P>

For JBoss JMS configuration is used batch file "06 CLI Config Queue & Topic.bat".

<P>Initial actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Go to page <a href="http://localhost:8080/Study03/">http://localhost:8080/Study03/</a>
	and select "Research Java Message Service".<BR/>
<img src="images/orangeCircle.png"> 2. Open 2nd browser tab with link "Open Page in New Browser Tab".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/j_m_s_Controls.png" width="1200" /><BR/>
<I>Screen from "Research Java Message Service" page controls.</I></P>

<P><IMG src="images/DiagramQueuesAndTopics.png" height="800" width="1100"/><BR>
<I>Queues &amp; topics diagram.</I></P>
<HR/>

<H3 id="j_m_s_sync">JMS Example 'Synchronous Queue &amp; Topic'</H3>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. <I>(synchronous queue)</I> In the 2nd tab push button "Queue Receive (Sync)".<BR/>
<img src="images/orangeCircle.png"> 2. <I>(synchronous queue)</I> In the 1st tab push button "Queue Send (Sync)".<BR/>
<img src="images/orangeCircle.png"> 3. <I>(synchronous topic)</I> In the 2nd tab push button "Topic Receive (Sync)".<BR/>
<img src="images/orangeCircle.png"> 4. <I>(synchronous topic)</I> In the 1st tab push button "Topic Send (Sync)".<BR/>
<img src="images/orangeCircle.png"> 5. In the 1st tab click link "Reload Report".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/SynchronousQueueAndTopicSendReceive.png" height="472" width="374"/><BR>
<I>Screen from synchronous queue &amp; topic send/receive actions.</I></P>
<HR/>

<H3 id="j_m_s_async">JMS Example 'Asynchronous Queue &amp; Topic'</H3>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. <I>(asynchronous queue)</I> In the 1st tab push button "Queue Send (Async)".<BR/>
<img src="images/orangeCircle.png"> 2. <I>(asynchronous topic)</I> In the 1st tab push button "Topic Send (Async)".<BR/>
<img src="images/orangeCircle.png"> 3. In the 1st tab click link "Reload Report".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/AsynchronousQueueAndTopicSendReceive.png" height="426" width="370"/><BR>
<I>Screen from asynchronous queue &amp; topic send/receive actions.</I></P>
<HR/>

<H3>Other JMS test scenarios</H3>
<OL>"First send then receive" test scenario for synchronous queue:
	<LI>Send to queue.</LI>
	<LI>Restart server.</LI>
	<LI>Receive from queue.</LI>
	<LI>Result: messages were <B>not lost</B>.</LI>
</OL>
<OL>"First send then receive" test scenario for synchronous topic:
	<LI>Send to topic.</LI>
	<LI>Receive from topic.</LI>
	<LI>Because receiver waits for messages (page hangs), repeat the sending to topic.</LI>
	<LI>Result: messages sent in the 1st action were <B>lost</B>.</LI>
</OL>
<OL>"Two consumers" test scenario for synchronous queue:
	<LI>Open the 2nd & the 3rd tab.</LI>
	<LI>In the 2nd tab push button "Queue Receive (Sync)".</LI>
	<LI>In the 3rd tab push button "Queue Receive (Sync)".</LI>
	<LI>In the 1st tab push button "Queue Send (Sync)" <B>two times</B> and click link "Reload Report".</LI>
	<LI>Result: for two consumers two queues were used because this is the point-to-point messaging.</LI>
</OL>
<OL>"Two subscribers" test scenario for synchronous topic:
	<LI>open the 2nd & the 3rd tab.</LI>
	<LI>In the 2nd tab push button "Topic Receive (Sync)".</LI>
	<LI>In the 3rd tab push button "Topic Receive (Sync)".</LI>
	<LI>In the 1st tab push button "Queue Send (Sync)" <B>one time</B> and click link "Reload Report".</LI>
	<LI>Result: for two subscribers single topic was used because this is the publish/subscribe messaging.</LI>
</OL>

<HR/>

<H2 id="trans_c_m_t">❹ Research Container-Managed Transactions</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/ejb/src/main/java/kp/trans_c_m_t/">kp.trans_c_m_t</a>.<BR/>
<img src="images/aquaHR-500.png"></P>

<P>Initial action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Go to page <a href="http://localhost:8080/Study03/">http://localhost:8080/Study03/</a>
	and select "Research Container-Managed Transactions".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/transactions_c_m_t_Controls.png" width="1200" /><BR/>
<I>Screen from "Research Container-Managed Transactions" page controls.</I></P>

<P><IMG src="images/DiagramResearchCMT.png" height="1398" width="1200"/><BR>
<I>Research CMT sequence diagram.</I></P>
It was implemented 'Parcel' approving logic. It validates every new created 'Parcel' object.<BR/>
The 'Parcel' with odd id value (1, 3, 5, ...) is not approved and transaction is marked for rollback.<BR/>
The 'Parcel' with even id value (2, 4, 6, ...) is approved.

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Push button "Send to 'CreateParcelQueue'" three times and button "Reload Report".<BR/>
<img src="images/orangeHR-500.png"></P>

<OL>
	<LI>The message from the 'CreateParcelQueue' is consumed and a new 'Parcel' is created.</LI>
	<LI>The confirmation message is send to the 'ConfirmCreateParcelQueue'.</LI>
	<LI>The created 'Parcel' has odd id value and is not approved.</LI>
	<LI>Therefore the transaction is marked for rollback.</LI>
	<LI>The message is <B>redelivered</B> to the 'CreateParcelQueue' and from this message a new 'Parcel' is created.</LI>
	<LI>The confirmation message is send to the 'ConfirmCreateParcelQueue'.</LI>
	<LI>The created 'Parcel' has even id value and is approved.</LI>
	<LI>From the 'ConfirmCreateParcelQueue' is consumed only the 2nd confirmation message.</LI>
</OL>

<P><IMG src="images/CreateParcel1.png" height="196" width="575"/><BR>
<I>Screen from 1st "Send to 'CreateParcelQueue'" action</I></P>

<P><IMG src="images/CreateParcel2.png" height="197" width="575"/><BR>
<I>Screen from 2nd "Send to 'CreateParcelQueue'" action</I></P>

<P><IMG src="images/CreateParcel3.png" height="196" width="575"/><BR>
<I>Screen from 3rd "Send to 'CreateParcelQueue'" action</I></P>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Send to 'ReadParcelQueue'" and button "Reload Report".<BR/>
<img src="images/orangeHR-500.png"></P>

<P>All committed 'Parcel' objects in the database have even id values.<BR/>
This proves that all not approved 'Parcel' objects were rolled back.</P>

<P><IMG src="images/ReadParcels.png" height="106" width="575"/><BR>
<I>Screen from "Send to 'ReadParcelQueue'" action</I>.</P>

<P>Action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Push button "Show Audit" and button "Reload Report".<BR/>
<img src="images/orangeHR-500.png"></P>

<P>Auditing is always processed in a new transaction.<BR/>
Therefore the audit information was not lost when the main transaction was rolled back.</P>

<P><IMG src="images/ShowAudit.png" height="34" width="575"/><BR>
<I>Screen from "Show Audit" action</I></P>

<HR/>

<H2 id="trans_b_m_t">❺ Research Bean-Managed Transactions</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study03/tree/master/ejb/src/main/java/kp/trans_b_m_t/">kp.trans_b_m_t</a>.<BR/>
<img src="images/aquaHR-500.png"></P>

<P><I>
In a stateless session bean with bean-managed transactions,<BR/>
a business method must commit or roll back a transaction before returning.<BR/>
However, a stateful session bean does not have this restriction.<BR/>
In a stateful session bean with a JTA transaction,<BR/>
the association between the bean instance and the transaction is retained across multiple client calls.<BR/>
Even if each business method called by the client opens and closes the database connection,<BR/>
the association is retained until the instance completes the transaction.
</I></P>

<P>Initial action:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> Go to page <a href="http://localhost:8080/Study03/">http://localhost:8080/Study03/</a>
	and select "Research Bean-Managed Transactions".<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/transactions_b_m_t_Controls.png" width="1200" /><BR/>
<I>Screen from "Research Bean-Managed Transactions" page controls.</I></P>

<P><IMG src="images/DiagramResearchBMT.png" height="237" width="708"/><BR>
<I>Research BMT class diagram.</I></P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. Push button "Create Capsule" two times, button "Commit Transaction" and button "Read Capsule".<BR/>
<img src="images/orangeCircle.png"> 2. Push button "Create Capsule" and button "Read Capsule".<BR/>
<img src="images/spacer-32.png">This step has created not committed 'Capsule' object.<BR/>
<img src="images/orangeCircle.png"> 3. Push button "Rollback Transaction" and button "Read Capsule".<BR/>
<img src="images/spacer-32.png">It was rolled back to the last commit (point 1. above).<BR/>
<img src="images/orangeCircle.png"> 4. Push button "Delete Capsule" and button "Read Capsule".<BR/>
<img src="images/spacer-32.png">The "Delete Capsule" action deletes last created 'Capsule'<BR/>
<img src="images/orangeCircle.png"> 5. Push button "Rollback Transaction" and button "Read Capsule".<BR/>
<img src="images/spacer-32.png">Again, it was rolled back to the last commit (point 1. above).<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/CreateAndCommitCapsule.png" height="128" width="570"/><BR>
<I>Screen from  actions: 1st "Create Capsule", 2nd "Create Capsule", "Commit Transaction", "Read Capsule"</I></P>

<P><IMG src="images/CreateCapsule.png" height="105" width="570"/><BR>
<I>Screen from  actions: 3rd "Create Capsule", "Read Capsule"</I></P>

<P><IMG src="images/RollbackCreatedCapsule.png" height="82" width="570"/><BR>
<I>Screen from  actions: 1st "Rollback Transaction", "Read Capsule"</I></P>

<P><IMG src="images/DeleteCapsule.png" height="59" width="570"/><BR>
<I>Screen from  actions: "Delete Capsule", "Read Capsule"</I></P>

<P><IMG src="images/RollbackDeletedCapsule.png" height="83" width="570"/><BR>
<I>Screen from  actions: 2nd "Rollback Transaction", "Read Capsule"</I></P>

<HR/>
<A href="apidocs/index.html" >API Specifications</A>
(API was not commited to <B>GitHub</B>; this link should be active after local build with <I>'mvn javadoc'</I>)
<HR/>
</BODY>
</HTML>