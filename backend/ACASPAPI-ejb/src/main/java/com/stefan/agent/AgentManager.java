package com.stefan.agent;

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

    private AgentManager() {
        this.agents = new ArrayList<>();
        online = new ArrayList<>();
        loginListeners = new ArrayList<>();
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
        System.out.println("Attempting to register " + agent.getId().getType().getFullName());
        for (Agent u : this.agents) {
            String pkg = u.getId().getType().getModule() + "." + u.getId().getType().getName();
            if (pkg.equals(agent.getId().getType().getModule() + "." + agent.getId().getType().getName())) 
                throw new AgentExistsException();
        }
        System.out.println("Registering agent " + agent.getId().getType().getFullName());
        this.agents.add(agent);
        agent.init();
    }

    synchronized public void login(Agent agent) throws AgentRunErrorException {
        System.out.println("Attempting to start " + agent.getId().getType().getFullName());
        for (Agent currentAgent : this.agents) {
            if (currentAgent.getId().getType().getName().equals(agent.getId().getType().getName())
              && currentAgent.getId().getType().getModule().equals(agent.getId().getType().getModule())
            ) {
                System.out.println(">>> " + currentAgent.getId().getType().getFullName());
                String pkg = agent.getId().getType().getModule() + "." + agent.getId().getType().getName();
                String name = pkg + "_" + getRandomAgentName();
                System.out.println("Starting agent " + pkg + " with name " + name);
                online.add(new RunningAgent(name, agent));
                agent.handleStart();
                return;
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

    public void logout(Agent agent) {
        RunningAgent found = null;
        for (Agent currentAgent : this.agents) {
            if (agent.getId().equals(currentAgent.getId())) {
                for (LoginListener listener : this.loginListeners) {
                    listener.agentLoggedOut(agent);
                }
                agent.handleStop();
                // remove it from logged agents 
                for (RunningAgent u : online) {
                    if (u.getAgent().getId().equals(agent.getId())) {
                        found = u;
                    }
                }
            }
        }
        if (found != null) online.remove(found);
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
}
