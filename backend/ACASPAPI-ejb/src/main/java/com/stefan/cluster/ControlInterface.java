package com.stefan.cluster;

import java.util.Collection;

import com.stefan.data.Agent;

public interface ControlInterface {

    public void init();
    public void run();
    public void finish();
    public void nodeAdded(Node node);
    public void nodeRemoved(String alias);
    public Collection<Agent> getAllAgents();
    public Collection<Agent> getRunningAgents();
    public void onPing();
    public void onPong(Node node);
    public void setAgents(Collection<Agent> agents);

    public Node findNode(String alias);

    public void runAgent(Agent user);
}
