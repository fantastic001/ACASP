package com.stefan.agent;

import com.google.common.base.Optional;
import com.stefan.data.Agent;
import com.stefan.data.RunningAgent;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.swing.ListCellRenderer;
import javax.ws.rs.core.GenericType;

@Stateless
public class AgentManager {
    private ArrayList<Agent> agents;
    private ArrayList<LoginListener> loginListeners;
    private ArrayList<RunningAgent> online;
    private static AgentManager instance = null;

    private Collection<RunningAgent> allOnlineAgents;

    private AgentManager() {
        this.agents = new ArrayList<>();
        online = new ArrayList<>();
        loginListeners = new ArrayList<>();
        allOnlineAgents = new ArrayList<>();
    }

    @PostConstruct
    public void agentLookup() {
        // Reflections reflections = new Reflections(new
        // ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.stefan.data"))
        // .setScanners(new SubTypesScanner()));
        // System.err.println("Classes annotated with SampleAnnotation");
        // for (Class clss : reflections.getSubTypesOf(Agent.class)) {
        // System.out.println(clss.getName());
        // try {
        // Agent agent = (Agent) clss.getConstructor().newInstance();
        // agent.init();
        // AgentManager.getInstance().registerAgent(agent);
        // } catch (InstantiationException | IllegalAccessException |
        // IllegalArgumentException
        // | InvocationTargetException | NoSuchMethodException | SecurityException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (AgentExistsException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
        ServiceLoader<Agent> loader = ServiceLoader.load(Agent.class);
        for (Agent agent : loader) {
            try {
                System.out.println("Loading agent " + agent.getId().getType().getName());
                AgentManager.getInstance().registerAgent(agent);
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

    synchronized public void registerAgent(Agent agent) throws AgentExistsException {
        for (Agent u : this.agents) {
            String pkg = u.getId().getType().getModule() + "." + u.getId().getType().getName();
            if (pkg.equals(agent.getId().getType().getModule() + "." + agent.getId().getType().getName())) 
                throw new AgentExistsException();
        }
        System.out.println("Registering agent " + agent.getId().getType().getFullName());
        this.agents.add(agent);
        agent.init();
    }

    synchronized public RunningAgent login(String name, Agent agent) throws AgentRunErrorException {
        for (Agent currentAgent : this.agents) {
            if (currentAgent.getId().getType().getName().equals(agent.getId().getType().getName())
              && currentAgent.getId().getType().getModule().equals(agent.getId().getType().getModule())
            ) {
                String pkg = agent.getId().getType().getFullName();
                System.out.println("Starting agent " + pkg + " with name " + name);
                RunningAgent ra =  new RunningAgent(name, agent, agent.getId());
                online.add(ra);
                allOnlineAgents.add(ra);
                agent.handleStart();
                ra.setId(agent.getId());
                for (LoginListener listener : loginListeners) {
                    listener.agentLoggedIn(ra);
                }
                return ra;
            }
        }
        throw new AgentRunErrorException();
    }
    private String getRandomAgentName() {
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
		return "Agent-" + generatedString;
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

    public Collection<Agent> getAgents() {
        return this.agents;
    }

    public void setAllOnlineAgents(Collection<RunningAgent> agents) {
        for (RunningAgent a : agents) System.out.println(a.getName());
        allOnlineAgents = agents;
    }

    public Collection<RunningAgent> getAllOnlineAgents() {
        return allOnlineAgents;
    }
}

