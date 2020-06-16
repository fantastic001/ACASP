package com.stefan.agent;

import com.stefan.data.Agent;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.swing.ListCellRenderer;

@Stateless
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

    @PostConstruct
    public void agentLookup() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forClassLoader())
                .setScanners(new SubTypesScanner()));
        System.err.println("Classes annotated with SampleAnnotation");
        for (Class clss : reflections.getSubTypesOf(Agent.class)) {
            System.out.println(clss.getName());
            try {
                Agent agent = (Agent) clss.getConstructor().newInstance();
                agent.init();
                AgentManager.getInstance().registerAgent(agent);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AgentExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

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
