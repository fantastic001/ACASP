package com.stefan.message;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;

import com.stefan.cluster.Control;
import com.stefan.cluster.ControlInterface;
import com.stefan.data.Agent;

@Startup
@Singleton
@LocalBean
public class JMSFactory {
	private Connection connection;
	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(lookup = "java:jboss/exported/jms/queue/stefan")
	private Queue defaultQueue;


    private String getRandomId() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
	@PostConstruct
	public void postConstruct() {
		try {
			// System.out.println("Creating jms factory");
			//connection = connectionFactory.createConnection(siebog.nodemanager.Global.USERNAME, siebog.nodemanager.Global.PASSWORD);
			connection = connectionFactory.createConnection("guest", "guest.guest.1");
			connection.setClientID(getRandomId());
			// System.out.println("start jms factory");
			connection.start();
			// System.out.println("jms construction done");
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@PreDestroy
	public void preDestroy() {
		try {
			connection.close();
		} catch (JMSException ex) {
			System.out.println("Exception while closing the JMS connection. " + ex);
		}
	}

	public Session getSession() {
		try {
			return connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	}

	public MessageProducer getDefaultProducer(Session session) {
		try {
			return session.createProducer(defaultQueue);
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
