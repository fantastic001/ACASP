package com.stefan.agent;

import com.stefan.data.ACLMessage;
import com.stefan.data.Agent;
import com.stefan.data.RunningAgent;

public interface LoginListener {
    public void agentLoggedIn(RunningAgent agent);
    public void agentLoggedOut(RunningAgent agent);
    public void messageSent(ACLMessage msg); 

}
