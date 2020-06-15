
package com.stefan.message;

import java.util.List;

import com.stefan.data.ACLMessage;

public interface MessageManager {
	
	List<String> getPerformatives();

	void post(ACLMessage message);

	void post(ACLMessage message, long delayMillisec);

	String ping();
}