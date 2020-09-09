package com.stefan.cluster;

import java.util.ArrayList;
import java.util.Collection;

import com.stefan.agent.AgentManager;
import com.stefan.data.ACLMessage;
import com.stefan.data.Agent;
import com.stefan.data.RunningAgent;
import com.stefan.message.MessageManager;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public class MasterNode implements ControlInterface {
    

    private ArrayList<Node> nodes;


    @Override
    public void init() {
        System.out.println("Initializing master node");
        nodes = new ArrayList<>();
    }

    @Override
    public void run() {
        // pinging all nodes 
    }

    @Override
    public void finish() {
        
    }
    
    @Override
    public void nodeAdded(Node node) {
        System.out.println("Adding node: " + node.getAlias());
        for (Node current : nodes) {
            current.post("/node/", node);
            node.postAsync("/nodes/", current);
        }
        for (RunningAgent a : AgentManager.getInstance().getAllOnlineAgents()) {
            System.out.println(a.getName());
        }
        node.postAsync("/agents/running/", AgentManager.getInstance().getAllOnlineAgents());
        // for (RunningAgent a : AgentManager.getInstance().getOnlineAgents()) {
        //     node.putAsync("/agents/running/" + a.getAgent().getId().getType().getFullName() + "/" + a.getName());
        // }
        
        nodes.add(node);
       
    }

    @Override
    public void nodeRemoved(String alias) {
        Node node = null; 
        for (Node n : nodes) {
            if (n.getAlias().equals(alias)) node = n;
        }
        if (node != null) {
            System.out.println("Removing node from registered nodes: " + node.getAlias());
            nodes.remove(node);
            ArrayList<RunningAgent> agentsToStay = new ArrayList<>();
            for (RunningAgent x : AgentManager.getInstance().getAllOnlineAgents()) {
                if (! x.getNodeAlias().equals(node.getAlias())) {
                    agentsToStay.add(x);
                }
            }
            AgentManager.getInstance().setAllOnlineAgents(agentsToStay);
        }
        for (Node n : nodes) {
            n.deleteAsync("/node/" + node.getAlias());
            n.postAsync("/agents/running/", AgentManager.getInstance().getAllOnlineAgents());
        }
    }
    @Override
    public void onPing() {
        Collection<Node> removal = new ArrayList<>();
        for (Node node : nodes) {
            // System.out.println("Health check for " + node.getAlias());
            try {
                node.getAsync("/node/").get(1, TimeUnit.SECONDS);
            } 
            catch (InterruptedException e) {

            }
            catch (TimeoutException e) {
                try {
                    node.getAsync("/node/").get(5, TimeUnit.SECONDS);
                }
                catch (TimeoutException e2) {
                    removal.add(node);
                }
                catch (ExecutionException e2) {
                    removal.add(node);
                }
                catch (InterruptedException e2) {
                    
                }
            }
            catch (ExecutionException e) {
                e.printStackTrace();
                removal.add(node);
            }
        }
        for (Node n : removal) {
            this.nodeRemoved(n.getAlias());
        }
    }

    @Override
    public void onPong(Node node) {
        
    }


    @Override
    public Node findNode(String alias) {
        if (alias.equals("") || alias.equals("master")) return null;
        for (Node node : nodes) {
            if (node.getAlias().equals(alias)) return node;
        }
        return null;
    }


    @Override
    public Collection<RunningAgent> getRunningAgents() {
        return AgentManager.getInstance().getOnlineAgents();
    }

    @Override
    public void runAgent(RunningAgent agent) {
        agent.setNodeAlias("master");
        for (Node node : nodes) {
            node.postAsync("/agents/running/", AgentManager.getInstance().getAllOnlineAgents());
        }
    }

    public boolean postMessage(ACLMessage message) {
        // System.out.println("Master node recieved message either from itself or from other nodes");
        message.setInReplyTo("master");
        for (Node node : nodes) {
            node.postAsync("/messages/", message);
        }
        return true;
    }

    @Override
    public void setRunningAgents(Collection<RunningAgent> agents) {
        AgentManager.getInstance().getOnlineAgents().clear();
        for (RunningAgent agent : agents) {
            AgentManager.getInstance().getOnlineAgents().add(agent);
        }
    }
    public void agentRemoved(String name) {
        for (Node node : nodes) {
            node.postAsync("/agents/running/", AgentManager.getInstance().getAllOnlineAgents());
        }
    }

}
