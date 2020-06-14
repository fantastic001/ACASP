package com.stefan.agent;

import com.stefan.data.Agent;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListCellRenderer;

public class AgentManager {
    private ArrayList<Agent> agents;
    private ArrayList<LoginListener> loginListeners;
    private ArrayList<Agent> online;
    private static AgentManager instance = null; 
    private AgentManager() {
        this.agents = new ArrayList<>();
        online = new ArrayList<>();
        loginListeners = new ArrayList<>();
    }

    public static AgentManager getInstance() {
        if (AgentManager.instance == null) AgentManager.instance = new AgentManager();
        return AgentManager.instance;
    }

    public void registerAgent(Agent agent) throws AgentExistsException {
        for (Agent u : this.agents) {
            if (u.getId().equals(agent.getId())) throw new AgentExistsException();
        }
        System.out.println("Registering agent");
        this.agents.add(agent);
    }

    public void login(Agent agent) throws AgentRunErrorException {
        for (Agent currentAgent : this.agents) {
            if (agent.getId().equals(currentAgent.getId())) {
                System.out.println("Logging agent in");
                for (LoginListener listener : this.loginListeners) {
                    listener.agentLoggedIn(agent);
                }
                // if already logged in ignore it 
                int count = 0;
                for (Agent u : online) {
                    if (u.getId().equals(agent.getId())) count++;
                }
                if (count == 0) {
                    System.out.println("Adding agent to online list");
                    online.add(agent);
                }
                return;
            }
        }
        throw new AgentRunErrorException();
    }

    public void logout(Agent agent) {
        Agent found = null;
        for (Agent currentAgent : this.agents) {
            if (agent.getId().equals(currentAgent.getId())) {
                for (LoginListener listener : this.loginListeners) {
                    listener.agentLoggedOut(agent);
                }
                // remove it from logged agents 
                for (Agent u : online) {
                    if (u.getId().equals(agent.getId())) {
                        found = u;
                    }
                }
            }
        }
        if (found != null) online.remove(found);
    }

    public Collection<Agent> getOnlineAgents() {
        return this.online;
    }
    public void addLoginListener(LoginListener listener) {
        this.loginListeners.add(listener);
    }

    public Collection<Agent> getAgents() {
        return this.agents;
    }
}
