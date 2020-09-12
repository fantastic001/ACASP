package com.stefan.message;

import java.util.Optional;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.xml.registry.FindQualifier;

import com.stefan.agent.AgentManager;
import com.stefan.agent.LoginListener;
import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Agent;
import com.stefan.data.RunningAgent;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/stefan")
})
public class MDBConsumer implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		try {
			processMessage(msg);
		} catch (JMSException ex) {
			System.out.println("Cannot process an incoming message. " + ex);
		}
	}

	private void processMessage(Message msg) throws JMSException {
		ACLMessage acl = (ACLMessage) ((ObjectMessage) msg).getObject();
		AID aid = getAid(msg, acl);
		deliverMessage(acl, aid);
	}
	
	private AID getAid(Message msg, ACLMessage acl) throws JMSException {
		int i = msg.getIntProperty("AIDIndex");
		return acl.getReceivers().get(i);
	}

	private void deliverMessage(ACLMessage msg, AID aid) {
		Optional<RunningAgent> agent = AgentManager.getInstance().getOnlineAgents().stream().filter(a -> a.getAgent().getId().equals(aid)).findFirst();
		if (agent.isPresent()) {
			// System.out.println("Agent found: " + agent.get().getName());
			for (LoginListener listener : AgentManager.getInstance().getLoginListeners()) {
				listener.messageSent(msg);
			}
			agent.get().getAgent().handleMessage(msg);
		} else {
			// System.out.println("No such agent: " + aid.getName());
		}
	}
}
