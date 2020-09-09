package com.stefan.cluster;

import java.util.Collection;

import com.stefan.data.ACLMessage;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.data.RunningAgent;

public interface ControlInterface {

    public void init();
    public void run();
    public void finish();
    public void nodeAdded(Node node);
    public void nodeRemoved(String alias);
    public Collection<RunningAgent> getRunningAgents();
    public void onPing();
    public void onPong(Node node);

    public boolean postMessage(ACLMessage message);
    public Node findNode(String alias);

    public void runAgent(RunningAgent agent);

    public void setRunningAgents(Collection<RunningAgent> agents);

    public void agentRemoved(String name);
}
