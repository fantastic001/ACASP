package com.stefan.agent;

import com.google.common.base.Optional;
import com.stefan.cluster.ResourceReader;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.data.RunningAgent;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.swing.ListCellRenderer;
import javax.ws.rs.core.GenericType;

@Stateless
public class AgentManager {
    private ArrayList<LoginListener> loginListeners;
    private ArrayList<RunningAgent> online;
    private static AgentManager instance = null;

    private Collection<RunningAgent> allOnlineAgents;

    private AgentManager() {
        online = new ArrayList<>();
        loginListeners = new ArrayList<>();
        allOnlineAgents = new ArrayList<>();
    }

    public Collection<AgentType> getAgentTypes() {
        ArrayList<AgentType> retval = new ArrayList<>();
        Collection<String> classes = new ResourceReader().getAgentClasses();
        for (String aClass : classes) {
            try {
                Agent sampleAgent = (Agent) Class.forName(aClass).newInstance();
                AgentType type =  sampleAgent.getId().getType();
                retval.add(type);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return retval;
    }

    public Agent agentLookup(String type) {
        Collection<String> classes = new ResourceReader().getAgentClasses();
        for (String aClass : classes) {
            try {
                System.out.println("Finding class " + aClass);
                Agent sampleAgent = (Agent) Class.forName(aClass).newInstance();
                if (sampleAgent.getId().getType().getFullName().equals(type)) {
                    return sampleAgent;
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static AgentManager getInstance() {
        if (AgentManager.instance == null) AgentManager.instance = new AgentManager();
        return AgentManager.instance;
    }

    synchronized public RunningAgent login(String name, Agent agent, Map<String, String[]> params) throws AgentRunErrorException {
        String pkg = agent.getId().getType().getFullName();
        System.out.println("Starting agent " + pkg + " with name " + name);
        RunningAgent ra =  new RunningAgent(name, agent, agent.getId());
        online.add(ra);
        allOnlineAgents.add(ra);
        agent.handleStart(params);
        ra.setId(agent.getId());
        for (LoginListener listener : loginListeners) {
            listener.agentLoggedIn(ra);
        }
        return ra;
    }

    public void logout(String name) {
        Collection<RunningAgent> toRemove = this.online.stream()
            .filter(agent -> (agent.getName().equals(name)))
            .collect(Collectors.toList());
        for (RunningAgent x : toRemove) {
            System.out.println("Removing agent: " + x.getName());
            this.online.remove(x);
            this.allOnlineAgents.remove(x);
            for (LoginListener listener : this.loginListeners) {
                listener.agentLoggedOut(x);
            }
        }
        this.setAllOnlineAgents(this.allOnlineAgents);
    }

    public Collection<RunningAgent> getOnlineAgents() {
        return this.online;
    }
    public void addLoginListener(LoginListener listener) {
        this.loginListeners.add(listener);
    }

    public Collection<LoginListener> getLoginListeners() {
        return loginListeners;
    }

    public void setAllOnlineAgents(Collection<RunningAgent> agents) {
        for (RunningAgent a : agents) System.out.println(a.getName());
        allOnlineAgents = agents;
    }

    public Collection<RunningAgent> getAllOnlineAgents() {
        return allOnlineAgents;
    }
}

