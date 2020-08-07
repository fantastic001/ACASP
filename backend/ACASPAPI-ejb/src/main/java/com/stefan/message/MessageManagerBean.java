
package com.stefan.message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.LocalBean;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Performative;

@Startup
@Singleton
@LocalBean
public class MessageManagerBean implements MessageManager {

	@EJB
	private JMSFactory factory;

	private Session session;
	private MessageProducer defaultProducer;

	@PostConstruct
	public void postConstruct() {
		System.out.println("invoking postconstruct");
		session = factory.getSession();
		defaultProducer = factory.getDefaultProducer(session);
		System.out.println("done postconstruct");
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
		}
	}

	@Override
	public List<String> getPerformatives() {
		final Performative[] arr = Performative.values();
		List<String> list = new ArrayList<>(arr.length);
		for (Performative p : arr)
			list.add(p.toString());
		return list;
	}

	@Override
	public void post(ACLMessage msg) {
		post(msg, 0L);
	}

	@Override
	public void post(ACLMessage msg, long delayMillisec) {
		System.out.println("Finding agents to which send message...");
		for (int i = 0; i < msg.getReceivers().size(); i++) {
			if (msg.getReceivers().get(i) == null) {
				throw new IllegalArgumentException("AID cannot be null.");
			}
			System.out.println("Sending message to agent");
			postToReceiver(msg, i, delayMillisec);
		}
	}

	@Override
	public String ping() {
		return "Pong from " + System.getProperty("jboss.node.name");
	}

	private void postToReceiver(ACLMessage msg, int index, long delayMillisec) {
		AID aid = msg.getReceivers().get(index);
		try {
			ObjectMessage jmsMsg = session.createObjectMessage(msg);
			setupJmsMsg(jmsMsg, aid, index, delayMillisec);
			if (delayMillisec == 0) {
				getProducer(msg).send(jmsMsg);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void setupJmsMsg(ObjectMessage jmsMsg, AID aid, int index, long delayMillisec) throws JMSException {
		jmsMsg.setStringProperty("JMSXGroupID", aid.getName());
		jmsMsg.setIntProperty("AIDIndex", index);
		jmsMsg.setStringProperty("_HQ_DUPL_ID", UUID.randomUUID().toString());
	}

	private MessageProducer getProducer(ACLMessage msg) {
		return defaultProducer;
	}

}
